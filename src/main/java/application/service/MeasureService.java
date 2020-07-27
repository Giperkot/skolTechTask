package application.service;

import application.dto.AvgResultDto;
import application.dto.MeasureResultDto;
import application.dto.SimpleResultDto;
import application.entity.AvgResultEntity;
import application.entity.MeasureResultEntity;
import application.exception.UserException;
import application.repository.MeasureResultDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasureService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MeasureService.class);

    private MeasureResultDao measureResultDao;

    @Autowired
    public MeasureService(MeasureResultDao measureResultDao) {
        this.measureResultDao = measureResultDao;
    }

    public List<MeasureResultDto> getAllMeasures() {

        List<MeasureResultEntity> measureResultEntityList = measureResultDao.getAll();

        List<MeasureResultDto> result = new ArrayList<>();

        for (MeasureResultEntity measureResultEntity : measureResultEntityList) {
            result.add(new MeasureResultDto(measureResultEntity));
        }

        return result;
    }

    public List<MeasureResultDto> getHistory(Long sensorId, Long from, Long to) {

        if (sensorId == null) {
            throw new UserException("Отсутствует id датчика");
        }

        if (from == null) {
            from = 0L;
        }

        if (to == null) {
            to = Long.MAX_VALUE / 1000_000;
        }

        List<MeasureResultDto> result = new ArrayList<>();
        List<MeasureResultEntity> measureResultEntityList = measureResultDao.getHistory(sensorId, from, to);

        for (MeasureResultEntity measureResultEntity : measureResultEntityList) {
            result.add(new MeasureResultDto(measureResultEntity));
        }

        return result;

    }

    public List<MeasureResultDto> getLatest(Long objectId) {

        if (objectId == null) {
            throw new UserException("Отсутствует id объекта");
        }

        List<MeasureResultDto> result = new ArrayList<>();
        List<MeasureResultEntity> measureResultEntityList = measureResultDao.getLastestByObject(objectId);

        for (MeasureResultEntity measureResultEntity : measureResultEntityList) {
            result.add(new MeasureResultDto(measureResultEntity));
        }

        return result;
    }

    public List<AvgResultDto> getAverage () {

        List<AvgResultDto> result = new ArrayList<>();

        List<AvgResultEntity> avgList = measureResultDao.getAvgGroupByObject();

        for (AvgResultEntity avgResultEntity : avgList) {
            result.add(new AvgResultDto(avgResultEntity));
        }

        return result;

    }

    public SimpleResultDto saveMeasure(List<MeasureResultDto> measureResultDtoList) {

        if (measureResultDtoList == null || measureResultDtoList.isEmpty()) {
            throw new UserException("Не переданы измерения");
        }

        SimpleResultDto result = new SimpleResultDto();

        for (MeasureResultDto measureResultDto : measureResultDtoList) {
            MeasureResultEntity measureResultEntity = measureResultDto.toEntity();
            measureResultDao.addMeasureResult(measureResultEntity);
        }

        result.setSuccess(true);
        result.setMessage("Записи успешно добавлены");

        return result;
    }

}
