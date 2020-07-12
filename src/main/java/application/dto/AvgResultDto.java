package application.dto;

import application.entity.AvgResultEntity;

import javax.persistence.Entity;

@Entity
public class AvgResultDto {

    private Long id;

    private Double value;

    public AvgResultDto() {
    }

    public AvgResultDto(AvgResultEntity avgResultEntity) {
        this.id = avgResultEntity.getId();
        this.value = avgResultEntity.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
