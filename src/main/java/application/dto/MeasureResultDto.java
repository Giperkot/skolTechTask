package application.dto;

import application.entity.MeasureResultEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.sql.Timestamp;
import java.time.Instant;

public class MeasureResultDto extends ADto {

    private long objectId;

    private long sensorId;

    private double value;

    @JsonProperty(value = "time")
    private Long messureTime;

    public MeasureResultDto() {
    }

    public MeasureResultDto(MeasureResultEntity measureResultEntity) {

        objectId = measureResultEntity.getObjectId();
        sensorId = measureResultEntity.getSensorId();
        value = measureResultEntity.getValue();
        messureTime = measureResultEntity.getMessureTime().getTime() / 1000;

    }

    public MeasureResultEntity toEntity () {
        MeasureResultEntity measureResultEntity = new MeasureResultEntity();

        measureResultEntity.setObjectId(this.getObjectId());
        measureResultEntity.setSensorId(this.getSensorId());

        Timestamp measureTime = Timestamp.from(Instant.ofEpochMilli(this.getMessureTime() * 1000));
        measureResultEntity.setMessureTime(measureTime);
        measureResultEntity.setValue(this.getValue());

        return measureResultEntity;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Long getMessureTime() {
        return messureTime;
    }

    public void setMessureTime(Long messureTime) {
        this.messureTime = messureTime;
    }
}
