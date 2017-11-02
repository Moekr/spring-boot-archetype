package ${groupId}.${subPackage}.web.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class SampleEntityDto {
    @NotBlank
    private String name;
    @NotBlank
    private String content;
}
