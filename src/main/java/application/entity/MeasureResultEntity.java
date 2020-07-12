package application.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "measure_result", schema = "sensor")
public class MeasureResultEntity extends AEntity {

    @Column(name = "object_id")
    private long objectId;

    @Column(name = "sensor_id")
    private long sensorId;

    @Column(name = "value")
    private double value;

    @Column(name = "measure_time")
    private Timestamp messureTime;

    public MeasureResultEntity() {
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

    public Timestamp getMessureTime() {
        return messureTime;
    }

    public void setMessureTime(Timestamp messureTime) {
        this.messureTime = messureTime;
    }
}
