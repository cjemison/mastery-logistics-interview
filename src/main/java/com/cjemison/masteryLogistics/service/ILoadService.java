package com.cjemison.masteryLogistics.service;

import com.cjemison.masteryLogistics.domain.LoadWrapper;
import com.cjemison.masteryLogistics.domain.ShipmentDO;
import com.cjemison.masteryLogistics.domain.TruckDO;
import java.util.List;
import reactor.core.publisher.Mono;

public interface ILoadService {

  Mono<LoadWrapper> load(final List<TruckDO> trucks,
      final List<ShipmentDO> shipments);
}
