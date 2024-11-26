package org.acsi.controller;

import com.stripe.exception.StripeException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acsi.dto.ActiveParkingSessionDto;
import org.acsi.dto.PaymentDto;
import org.acsi.exceptions.ActiveParkingSessionNotFound;
import org.acsi.request.ParkingSessionRequest;
import org.acsi.response.ApiResponse;
import org.acsi.service.PaymentService;

import java.util.List;
import java.util.Map;

@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentController {

    @Inject
    PaymentService paymentService;

    @GET
    public ApiResponse getAllPayments() {
        try {
            List<PaymentDto> payments = paymentService.getAllPayments();
            return new ApiResponse("Payments obtained successfully!", payments);
        } catch (ActiveParkingSessionNotFound e) {
            return new ApiResponse(e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse("Unknown error!", null);
        }
    }

    @POST
    @Path("/{id}")
    @Transactional
    public Response createCheckoutSession(@PathParam("id") Long id) {
        try {
            return paymentService.createCheckoutSession(id);
        } catch (StripeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
