package ${groupId}.${subPackage}.web.controller.api;

import ${groupId}.${subPackage}.logic.service.SampleEntityService;
import ${groupId}.${subPackage}.util.ToolKit;
import ${groupId}.${subPackage}.web.dto.SampleEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SampleEntityController {
    private final SampleEntityService entityService;

    @Autowired
    public SampleEntityController(SampleEntityService entityService) {
        this.entityService = entityService;
    }

    @PostMapping("/entities")
    public Map<String, Object> createSampleEntity(@RequestBody @Valid SampleEntityDto entityDto){
        return ToolKit.assemblyResponseBody(entityService.createSampleEntity(entityDto));
    }

    @GetMapping("/entities")
    public Map<String, Object> getSampleEntities(){
        return ToolKit.assemblyResponseBody(entityService.getSampleEntities());
    }

    @GetMapping("/entity/{entityId}")
    public Map<String, Object> getSampleEntity(@PathVariable int entityId){
        return ToolKit.assemblyResponseBody(entityService.getSampleEntity(entityId));
    }

    @PutMapping("/entity/{entityId}")
    public Map<String, Object> updateSampleEntity(@PathVariable int entityId, @RequestBody @Valid SampleEntityDto entityDto){
        return ToolKit.assemblyResponseBody(entityService.updateSampleEntity(entityId, entityDto));
    }

    @DeleteMapping("/entity/{entityId}")
    public Map<String, Object> deleteSampleEntity(@PathVariable int entityId){
        entityService.deleteSampleEntity(entityId)
        return ToolKit.emptyResponseBody();
    }
}
