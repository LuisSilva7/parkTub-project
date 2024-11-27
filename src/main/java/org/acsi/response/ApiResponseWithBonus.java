package org.acsi.response;

public class ApiResponseWithBonus {
    public String message;
    public Object data;
    public int points;

    public ApiResponseWithBonus(String message, Object data, int points) {
        this.message = message;
        this.data = data;
        this.points = points;
    }
}
