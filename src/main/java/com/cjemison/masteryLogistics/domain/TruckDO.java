package com.cjemison.masteryLogistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = TruckDO.Builder.class)
public class TruckDO {

  private final Integer id;
  private final Integer capacity;

  protected TruckDO(final Integer id,
      final Integer capacity) {
    this.id = id;
    this.capacity = capacity;
  }

  public static TruckDO.Builder builder() {
    return new TruckDO.Builder();
  }

  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  @JsonProperty("capacity")
  public Integer getCapacity() {
    return capacity;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TruckDO)) {
      return false;
    }
    final TruckDO truckDO = (TruckDO) o;
    return Objects.equals(getId(), truckDO.getId()) &&
        Objects.equals(getCapacity(), truckDO.getCapacity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getCapacity());
  }

  @Override
  public String toString() {
    return "TruckDO{" +
        "id=" + id +
        ", capacity=" + capacity +
        '}';
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Builder {

    private Integer id;
    private Integer capacity;

    public Builder() {
      this.id = 0;
      this.capacity = 0;
    }

    @JsonProperty("id")
    public Builder id(final Integer value) {
      if (value != null) {
        this.id = value;
      }
      return this;
    }

    @JsonProperty("capacity")
    public Builder capacity(final Integer value) {
      if (value != null) {
        this.capacity = value;
      }
      return this;
    }

    public TruckDO build() {
      return new TruckDO(id, capacity);
    }
  }
}
