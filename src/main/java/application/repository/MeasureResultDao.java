package application.repository;

import application.entity.AvgResultEntity;
import application.entity.MeasureResultEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

//import org.hibernate.Query;

@Repository
public class MeasureResultDao {

//    private EntityManagerFactory emf;

    /*@Autowired
    public MeasureResultDao(EntityManagerFactory emf) {
        this.emf = emf;
    }*/

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public void clearDataBase () {
        Query query = em.createNativeQuery("truncate sensor.measure_result cascade");
        Query query1 = em.createNativeQuery("truncate sensor.object cascade");
        Query query2 = em.createNativeQuery("truncate sensor.sensor cascade");

        query.executeUpdate();
        query1.executeUpdate();
        query2.executeUpdate();
    }

    @Transactional
    public void addMeasureResult (MeasureResultEntity measureResultEntity) {
        if (measureResultEntity.getId() == null) {
            Query query = em.createNativeQuery("select nextval('sensor.measure_result_id_seq')");

            BigInteger nextId = (BigInteger)query.getSingleResult();
            measureResultEntity.setId(nextId.longValue());
        }

        em.persist(measureResultEntity);
    }


    public List<MeasureResultEntity> getAll () {
        Query query = em.createNativeQuery("select * from sensor.measure_result", MeasureResultEntity.class);

        return query.getResultList();

    }

    public List<MeasureResultEntity> getHistory(Long sensorId, Long from, Long to) {

        String sql = "select * from sensor.measure_result mr " +
                "where mr.sensor_id = :sensorId and mr.measure_time between to_timestamp(:from) and to_timestamp(:to)";

        Query query = em.createNativeQuery(sql, MeasureResultEntity.class);

        query.setParameter("sensorId", sensorId);
        query.setParameter("from", from);
        query.setParameter("to", to);

        return query.getResultList();
    }

    public List<MeasureResultEntity> getLastestByObject(Long objectId) {
        String sql = "select t1.id, t1.create_time, t1.object_id, t1.sensor_id, t1.measure_time, t1.value from (\n" +
                "  select mr.id, mr.create_time, row_number() over (partition by mr.sensor_id " +
                "       order by measure_time desc) as row_number, object_id, sensor_id, measure_time, value from sensor.measure_result mr\n" +
                "  where mr.object_id = :objectId " +
                ") t1 where t1.row_number = 1;";

        Query query = em.createNativeQuery(sql, MeasureResultEntity.class);

        query.setParameter("objectId", objectId);

        return query.getResultList();
    }

    public List<AvgResultEntity> getAvgGroupByObject() {
        String sql = "select t2.object_id as id, avg(t2.value) as avg from ( " +
                "     select t1.object_id, t1.sensor_id, t1.measure_time, t1.value " +
                "     from (\n" +
                "              select row_number() over (partition by mr.sensor_id, mr.object_id " +
                "                  order by measure_time desc) as row_number, " +
                "                     object_id, " +
                "                     sensor_id, " +
                "                     measure_time, " +
                "                     value\n" +
                "              from sensor.measure_result mr) t1 " +
                "     where t1.row_number = 1 " +
                " ) t2 group by object_id";


        Query query = em.createNativeQuery(sql, AvgResultEntity.class);

        return query.getResultList();
    }

}
