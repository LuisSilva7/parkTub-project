package org.parkTub.carcountproducer.parkImageInfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/park-image-info")
@RequiredArgsConstructor
public class ParkImageInfoController {

    private final ParkImageInfoService parkImageInfoService;

    @PostMapping
    public void registerParkImageInfo(@RequestBody @Valid ParkImageInfoRequest request) {
        parkImageInfoService.registerParkImageInfo(request);
    }
}
