package org.acsi.response;

public class ApiResponseWithDiscount {
    public String message;
    public Object data;
    public double discount;

    public ApiResponseWithDiscount(String message, Object data, double discount) {
        this.message = message;
        this.data = data;
        this.discount = discount;
    }
}
