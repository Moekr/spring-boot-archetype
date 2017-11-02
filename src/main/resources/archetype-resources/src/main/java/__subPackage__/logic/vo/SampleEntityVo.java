package ${groupId}.${subPackage}.logic.vo;

import ${groupId}.${subPackage}.data.entity.SampleEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class SampleEntityVo {
    private int id;
    private String name;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public SampleEntityVo(SampleEntity entity){
        BeanUtils.copyProperties(entity, this);
    }
}
