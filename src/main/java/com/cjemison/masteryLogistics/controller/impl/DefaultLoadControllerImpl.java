package com.cjemison.masteryLogistics.controller.impl;

import com.cjemison.masteryLogistics.controller.ILoadController;
import com.cjemison.masteryLogistics.domain.LoadWrapper;
import com.cjemison.masteryLogistics.domain.ShipmentDO;
import com.cjemison.masteryLogistics.domain.TruckDO;
import com.cjemison.masteryLogistics.service.ILoadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Api("Endpoints for optimized shipping")
public class DefaultLoadControllerImpl implements ILoadController {

  private final List<TruckDO> trucks;
  private final List<ShipmentDO> shipments;
  private final ILoadService firstFit;

  @Autowired
  public DefaultLoadControllerImpl(@Qualifier("trucks") final List<TruckDO> trucks,
      @Qualifier("shipments") final List<ShipmentDO> shipments,
      @Qualifier("firstComeFirstLoaded") final ILoadService firstFit) {
    this.trucks = trucks;
    this.shipments = shipments;
    this.firstFit = firstFit;
  }

  @Override
  @GetMapping(value = "/v1/firstFit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiOperation("An endpoint for loading shipments into trucks as they come.")
  @ApiResponse(code = 200, message = "An endpoint for loading shipments in to trucks as they come.",
      response = LoadWrapper.class)
  public Mono<LoadWrapper> shipmentFirstFit() {
    return firstFit.load(trucks, shipments);
  }

  @Override
  @GetMapping(value = "/v1/sortFirstFit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiOperation("An endpoint for loading sorted shipments into  sorted trucks as they come.")
  @ApiResponse(code = 200, message = "An endpoint for loading sorted shipments into  sorted trucks as they come.",
      response = LoadWrapper.class)
  public Mono<LoadWrapper> sortShipmentFirstFit() {

    final List<TruckDO> truckDOS = trucks.stream()
        .sorted(Comparator.comparing(TruckDO::getCapacity))
        .collect(Collectors.toList());
    Collections.reverse(truckDOS);

    final List<ShipmentDO> shipmentDOS = shipments.stream()
        .sorted(Comparator.comparing(ShipmentDO::getCapacity))
        .collect(Collectors.toList());
    Collections.reverse(shipmentDOS);

    return firstFit.load(truckDOS, shipmentDOS);
  }
}
