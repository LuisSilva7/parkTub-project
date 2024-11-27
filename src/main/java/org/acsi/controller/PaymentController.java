package org.acsi.controller;

import com.stripe.exception.StripeException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acsi.dto.BonusDto;
import org.acsi.dto.PaymentDto;
import org.acsi.exceptions.ObjectNotFound;
import org.acsi.request.ActivateBonusRequest;
import org.acsi.request.PaymentRequest;
import org.acsi.response.ApiResponse;
import org.acsi.response.ApiResponseWithBonus;
import org.acsi.response.ApiResponseWithDiscount;
import org.acsi.service.PaymentService;

import java.util.List;

@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentController {

    @Inject
    PaymentService paymentService;

    @GET
    public ApiResponseWithDiscount getAllPayments() {
        try {
            List<PaymentDto> payments = paymentService.getAllPayments();
            Double discount = paymentService.getActiveDiscount();
            return new ApiResponseWithDiscount("Payments obtained successfully!", payments, discount);
        } catch (ObjectNotFound e) {
            return new ApiResponseWithDiscount(e.getMessage(), null, 0.0);
        } catch (Exception e) {
            return new ApiResponseWithDiscount("Unknown error!", null, 0.0);
        }
    }

    @POST
    @Path("/{id}")
    @Transactional
    public ApiResponse createCheckoutSession(@PathParam("id") Long id, PaymentRequest paymentRequest) {
        try {
            return new ApiResponse("Checkout created successfully!",
                    paymentService.createCheckoutSession(id, paymentRequest.discount));
        } catch (StripeException e) {
            return new ApiResponse("Unknown error!", null);
        }
    }

    @GET
    @Path("/bonus")
    public ApiResponseWithBonus getAllBonus() {
        try {
            List<BonusDto> bonus = paymentService.getAllBonus();
            int userPoints = paymentService.getUserPoints();
            return new ApiResponseWithBonus("Bonus obtained successfully!", bonus, userPoints);
        } catch (ObjectNotFound e) {
            return new ApiResponseWithBonus(e.getMessage(), null, 0);
        } catch (Exception e) {
            return new ApiResponseWithBonus("Unknown error!", null, 0);
        }
    }

    @POST
    @Path("/bonus/{id}")
    @Transactional
    public ApiResponse activateBonus(@PathParam("id") Long id, ActivateBonusRequest activateBonusRequest) {
        try {
            return new ApiResponse("Bonus activated successfully!", paymentService.activateBonus(id
                    , activateBonusRequest.bonusPoints));
        } catch (Exception e) {
            return new ApiResponse("Unknown error!", null);
        }
    }
}
