package application.service;

import application.dto.MeasureResultDto;
import application.entity.MeasureResultEntity;
import application.entity.ObjectEntity;
import application.entity.SensorEntity;
import application.exception.UserException;
import application.repository.MeasureResultDao;
import application.repository.ObjectDao;
import application.repository.SensorDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class DBCreaterService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MeasureService.class);

    private MeasureResultDao measureResultDao;

    private Environment environment;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private ObjectDao objectDao;

    private SensorDao sensorDao;

    private Set<Long> objectSet = new HashSet<>();

    private Set<Long> sensorSet = new HashSet<>();

    @Value( "${path.to.json.data}" )
    private String pathToJsonData;

    @Autowired
    public DBCreaterService(MeasureResultDao measureResultDao, Environment environment, SensorDao sensorDao,
                            ObjectDao objectDao) {
        this.measureResultDao = measureResultDao;
        this.environment = environment;
        this.objectDao = objectDao;
        this.sensorDao = sensorDao;
    }

    @PostConstruct
    public void initDataBase () throws IOException {
        LOGGER.info("!____ initDataBase ______!");
        String createDb = environment.getProperty("CreateDB");

        if ("false".equals(createDb)) {
            return;
        }

        String pathToJsonDataVal = new String(pathToJsonData.getBytes("ISO8859-1"), "UTF-8");

        if (pathToJsonDataVal == null || pathToJsonDataVal.isEmpty()) {
            throw new UserException("pathToJsonData is empty");
        }

        File dataJsonFile = new File (pathToJsonDataVal);

        if (!dataJsonFile.exists()) {
            throw new UserException("Не указан путь к data.json");
        }

        clearDatabase();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(dataJsonFile)));) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                int start = line.indexOf("{");
                int end = line.lastIndexOf("}");
                String measureObjectStr = line.substring(start, end + 1);

                MeasureResultDto measureResultDto = objectMapper.readValue(measureObjectStr, MeasureResultDto.class);

                addMeasureResult(measureResultDto);
            }
        }
    }

    private void addMeasureResult (MeasureResultDto measureResultDto) {
        if (!objectSet.contains(measureResultDto.getObjectId())) {
            ObjectEntity objectEntity = new ObjectEntity();
            objectEntity.setId(measureResultDto.getObjectId());
            objectDao.addObject(objectEntity);

            objectSet.add(measureResultDto.getObjectId());
        }

        if (!sensorSet.contains(measureResultDto.getSensorId())) {
            SensorEntity sensorEntity = new SensorEntity();
            sensorEntity.setId(measureResultDto.getSensorId());
            sensorDao.addSensor(sensorEntity);

            sensorSet.add(measureResultDto.getSensorId());
        }

        MeasureResultEntity measureResultEntity = measureResultDto.toEntity();

        measureResultDao.addMeasureResult(measureResultEntity);
    }


    public void clearDatabase () {
        measureResultDao.clearDataBase();
    }

    @PreDestroy
    public void preDestroy() {
        LOGGER.info("!____ preDestroy ______!");
    }


}
