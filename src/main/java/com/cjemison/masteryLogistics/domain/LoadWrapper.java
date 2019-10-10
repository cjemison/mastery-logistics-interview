package com.cjemison.masteryLogistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = ShipmentDO.Builder.class)
public class LoadWrapper {

  private final List<LoadDO> loadDOS;

  public LoadWrapper(final List<LoadDO> loadDOS) {
    this.loadDOS = loadDOS;
  }

  @JsonProperty("loads")
  public List<LoadDO> getLoadDOS() {
    return loadDOS;
  }

  @JsonProperty("total_number_of_loads")
  public Integer totalLoadCount() {
    return loadDOS.size();
  }
}
