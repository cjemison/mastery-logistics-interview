package com.cjemison.masteryLogistics.controller;

import com.cjemison.masteryLogistics.domain.LoadWrapper;
import reactor.core.publisher.Mono;

public interface ILoadController {

  /**
   * <p>The first-fit algorithm takes shipments in the order they come
   * and places them in the first bin in which they fit. </p>≤
   */
  Mono<LoadWrapper> shipmentFirstFit();

  /**
   * <p>The first-fit algorithm takes shipments as short items
   * and places them in the first bin in which they fit. </p>≤
   */
  Mono<LoadWrapper> sortShipmentFirstFit();

}
