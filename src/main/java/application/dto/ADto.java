package application.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class ADto {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
