package application.controller;

import application.dto.AvgResultDto;
import application.dto.MeasureResultDto;
import application.dto.SimpleResultDto;
import application.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MeasureController {

    private MeasureService measureService;

    @Autowired
    public MeasureController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @RequestMapping(value = "allMeasures", method = {RequestMethod.GET})
    public List<MeasureResultDto> getAllMeasures() {
        return measureService.getAllMeasures();
    }

    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public SimpleResultDto saveMeasure(@RequestBody List<MeasureResultDto> measureResultDtoList) {
        return measureService.saveMeasure(measureResultDtoList);
    }

    @RequestMapping(value = "history", method = {RequestMethod.GET})
    public List<MeasureResultDto> getHistory(@RequestParam(name = "id") Long sensorId,
                                             @RequestParam(name = "from", required = false)  Long from,
                                             @RequestParam(name = "to", required = false)  Long to) {
        return measureService.getHistory(sensorId, from, to);
    }

    @RequestMapping(value = "latest", method = {RequestMethod.GET})
    public List<MeasureResultDto> getLatest(@RequestParam(name = "id") Long objectId) {
        return measureService.getLatest(objectId);
    }

    @RequestMapping(value = "avg", method = {RequestMethod.GET})
    public List<AvgResultDto> getAverage() {
        return measureService.getAverage();

    }

    public MeasureService getMeasureService() {
        return measureService;
    }

    public void setMeasureService(MeasureService measureService) {
        this.measureService = measureService;
    }
}
