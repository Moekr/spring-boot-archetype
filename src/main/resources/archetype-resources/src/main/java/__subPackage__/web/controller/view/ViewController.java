package ${groupId}.${subPackage}.web.controller.view;

import ${groupId}.${subPackage}.logic.service.SampleEntityService;
import ${groupId}.${subPackage}.util.ToolKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class ViewController {
    private final SampleEntityService entityService;

    @Autowired
    public ViewController(SampleEntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping("/")
    public String index(Map<String, Object> parameterMap){
        parameterMap.put("entities", ToolKit.sort(entityService.getSampleEntities(), (a, b) -> b.getId()-a.getId()));
        return "index";
    }

    @GetMapping("/entity/{entityId}")
    public String entity(Map<String, Object> parameterMap, @PathVariable int entityId){
        parameterMap.put("entity", entityService.getSampleEntity(entityId));
        return "entity";
    }
}
