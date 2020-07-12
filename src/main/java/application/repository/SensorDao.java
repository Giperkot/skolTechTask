package application.repository;

import application.entity.ObjectEntity;
import application.entity.SensorEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SensorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addSensor(SensorEntity objectEntity) {
        entityManager.persist(objectEntity);
    }

}
