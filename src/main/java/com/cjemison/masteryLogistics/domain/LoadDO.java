package com.cjemison.masteryLogistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = ShipmentDO.Builder.class)
public class LoadDO {

  private final Integer id;
  private final TruckDO truckDO;
  private final List<ShipmentDO> shipmentDOList;

  protected LoadDO(final Integer id,
      final TruckDO truckDO,
      final List<ShipmentDO> shipmentDOList) {
    this.id = id;
    this.truckDO = truckDO;
    this.shipmentDOList = shipmentDOList;
  }

  public static LoadDO.Builder builder() {
    return new LoadDO.Builder();
  }

  @JsonProperty("load_id")
  public Integer getId() {
    return id;
  }

  @JsonProperty("truck")
  public TruckDO getTruckDO() {
    return truckDO;
  }

  @JsonProperty("shipments")
  public List<ShipmentDO> getShipmentDOList() {
    return shipmentDOList;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LoadDO)) {
      return false;
    }
    final LoadDO loadDO = (LoadDO) o;
    return Objects.equals(getId(), loadDO.getId()) &&
        Objects.equals(getTruckDO(), loadDO.getTruckDO()) &&
        Objects.equals(getShipmentDOList(), loadDO.getShipmentDOList());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTruckDO(), getShipmentDOList());
  }

  @Override
  public String toString() {
    return "LoadDO{" +
        "id=" + id +
        ", truckDO=" + truckDO +
        ", shipmentDOList=" + shipmentDOList +
        '}';
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Builder {

    private Integer id;
    private TruckDO truckDO;
    private List<ShipmentDO> shipmentDOList;

    public Builder() {
      this.id = 0;
      this.truckDO = TruckDO.builder().build();
      this.shipmentDOList = new LinkedList<>();
    }

    @JsonProperty("id")
    public Builder id(final Integer value) {
      if (value != null) {
        this.id = value;
      }
      return this;
    }

    @JsonProperty("truck")
    public Builder truck(final TruckDO value) {
      if (value != null) {
        this.truckDO = value;
      }
      return this;
    }

    @JsonProperty("shipments")
    public Builder shipments(final List<ShipmentDO> value) {
      if (CollectionUtils.isNotEmpty(value)) {
        this.shipmentDOList = value;
      }
      return this;
    }

    public Builder shipment(final ShipmentDO value) {
      if (value != null) {
        this.shipmentDOList.add(value);
      }
      return this;
    }

    public int getCurrentWeight() {
      return shipmentDOList.stream()
          .map(ShipmentDO::getCapacity)
          .reduce(0, Integer::sum);
    }


    public LoadDO build() {
      return new LoadDO(id, truckDO, shipmentDOList);
    }
  }
}
