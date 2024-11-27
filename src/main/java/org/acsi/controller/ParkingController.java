package org.acsi.controller;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acsi.dto.ActiveParkingSessionDto;
import org.acsi.dto.InactiveParkingSessionDto;
import org.acsi.dto.ParkingLotDto;
import org.acsi.exceptions.ObjectNotFound;
import org.acsi.request.ParkingSessionRequest;
import org.acsi.request.UpdateParkingSessionRequest;
import org.acsi.response.ApiResponse;
import org.acsi.service.ParkingService;

import java.util.List;

@Path("/parking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParkingController {

    @Inject
    Template index;

    @Inject
    ParkingService parkingService;

    @GET
    @Path("/test")
    public Response test() {
        String name = "luis";
        TemplateInstance templateInstance = index.data("name", name);
        return Response.ok(templateInstance).build();
    }

    @GET
    public ApiResponse getAllParkingLots() {
        try {
            List<ParkingLotDto> parkingLotDtos = parkingService.getAllParkingLots();
            return new ApiResponse("Parking lots obtained successfully!", parkingLotDtos);
        } catch (Exception e) {
            return new ApiResponse("Unknown error!", null);
        }
    }

    @GET
    @Path("/active-session")
    public ApiResponse getActiveParkingSession() {
        try {
            ActiveParkingSessionDto activeParkingSessionDto = parkingService.getActiveParkingSession();
            return new ApiResponse("Parking session obtained successfully!", activeParkingSessionDto);
        } catch (ObjectNotFound e) {
            return new ApiResponse(e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse("Unknown error!", null);
        }
    }

    @POST
    @Transactional
    public ApiResponse createParkingSession(ParkingSessionRequest parkingSessionRequest) {
        try {
            ActiveParkingSessionDto activeParkingSessionDto = parkingService
                    .createParkingSession(parkingSessionRequest);
            return new ApiResponse("Parking session created successfully!", activeParkingSessionDto);
        } catch (Exception e) {
            return new ApiResponse("Unknown error!", null);
        }
    }

    @PUT
    @Transactional
    public ApiResponse updateParkingSession(UpdateParkingSessionRequest updateParkingSessionRequest) {
        try {
            InactiveParkingSessionDto inactiveParkingSessionDto = parkingService
                    .updateParkingSession(updateParkingSessionRequest);
            return new ApiResponse("Parking session updated successfully!", inactiveParkingSessionDto);
        } catch (Exception e) {
            return new ApiResponse("Unknown error!", null);
        }
    }
}
