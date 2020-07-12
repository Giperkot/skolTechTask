package application.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@MappedSuperclass
public class AEntity {

    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @PrePersist
    protected void prePersist() {
        createTime = Timestamp.from(Instant.ofEpochMilli(System.currentTimeMillis()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
