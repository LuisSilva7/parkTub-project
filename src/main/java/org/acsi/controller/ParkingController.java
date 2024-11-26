package org.acsi.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acsi.dto.ActiveParkingSessionDto;
import org.acsi.exceptions.ActiveParkingSessionNotFound;
import org.acsi.request.ParkingSessionRequest;
import org.acsi.response.ApiResponse;
import org.acsi.service.ParkingService;

@Path("/parking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParkingController {

    @Inject
    ParkingService parkingService;

    @GET
    @Path("/active-session")
    public ApiResponse getActiveParkingSession() {
        try {
            ActiveParkingSessionDto activeParkingSessionDto = parkingService.getActiveParkingSession();
            return new ApiResponse("Parking session obtained successfully!", activeParkingSessionDto);
        } catch (ActiveParkingSessionNotFound e) {
            return new ApiResponse(e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse("Unknown error!", null);
        }
    }

    @POST
    @Transactional
    public ApiResponse createParkingSession(ParkingSessionRequest parkingSessionRequest) {
        try {
            ActiveParkingSessionDto activeParkingSessionDto = parkingService.createParkingSession(parkingSessionRequest);
            return new ApiResponse("Parking session created successfully!", activeParkingSessionDto);
        } catch (Exception e) {
            return new ApiResponse("Unknown error!", null);
        }
    }
}
