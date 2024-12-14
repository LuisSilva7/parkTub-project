package org.parkTub.payment.payment;

import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.parkTub.payment.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createPayment(
            @RequestBody @Valid PaymentRequest paymentRequest) {
        paymentService.createPayment(paymentRequest);

        return ResponseEntity.ok(new ApiResponse<>(
                "Payment created successfully!", null));
    }

    @GetMapping("/customer/{customer-id}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> findAllPayments(
            @PathVariable("customer-id") Long customerId) {
        List<PaymentResponse> paymentResponses = paymentService.findAllByCustomerId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Payments obtained successfully!", paymentResponses));
    }

    @PostMapping("/{payment-id}/checkout-session/customer/{customer-id}")
    public ResponseEntity<ApiResponse<String>> createCheckoutSession(
            @PathVariable("payment-id") Long paymentId,
            @RequestBody @Valid CheckoutSessionRequest request,
            @PathVariable("customer-id") Long customerId) {
        try {
            String checkoutSessionUrl = paymentService.createCheckoutSession(paymentId, request, customerId);

            return ResponseEntity.ok(new ApiResponse<>(
                    "Checkout created successfully!", checkoutSessionUrl));
        } catch (StripeException exp) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(
                    "Error creating checkout session!", null));
        }
    }
}
