package com.cjemison.masteryLogistics.service.impl;

import com.cjemison.masteryLogistics.domain.LoadDO;
import com.cjemison.masteryLogistics.domain.LoadDO.Builder;
import com.cjemison.masteryLogistics.domain.LoadWrapper;
import com.cjemison.masteryLogistics.domain.ShipmentDO;
import com.cjemison.masteryLogistics.domain.TruckDO;
import com.cjemison.masteryLogistics.service.ILoadService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>The first-fit algorithm takes shipments in the order they come
 * and places them in the first bin in which they fit. </p>≤
 */
@Service("firstComeFirstLoaded")
public class DefaultFirstComeFirstLoadImpl implements ILoadService {

  @Override
  public Mono<LoadWrapper> load(final List<TruckDO> trucks,
      final List<ShipmentDO> shipments) {
    return Mono.subscriberContext()
        .flatMap(context -> {

          final List<LoadDO> list = new ArrayList<>();
          final Set<Integer> set = new HashSet<>();
          final int max = shipments.size();
          int id = 1;

          while (set.size() < max) {
            // iterate the truck (bin)
            for (TruckDO truckDO : trucks) {
              Builder builder = LoadDO.builder().truck(truckDO).id(id);
              for (ShipmentDO shipmentDO : shipments) {
                if (!set.contains(shipmentDO.getId())) {
                  if (truckDO.getCapacity() > shipmentDO.getCapacity()) {
                    if (builder.getCurrentWeight() <= truckDO.getCapacity()) {
                      int currentCapacity = truckDO.getCapacity() - builder.getCurrentWeight();
                      if (shipmentDO.getCapacity() <= currentCapacity) {
                        builder.shipment(shipmentDO);
                        set.add(shipmentDO.getId());
                      }
                    }
                  }
                }
              }
              if (builder.getCurrentWeight() > 0) {
                list.add(builder.build());
                id++;
              }
            }
          }
          return Mono.just(new LoadWrapper(list));
        });

  }
}
