package application.repository;

import application.entity.ObjectEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ObjectDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addObject(ObjectEntity objectEntity) {
        entityManager.persist(objectEntity);
    }

}
