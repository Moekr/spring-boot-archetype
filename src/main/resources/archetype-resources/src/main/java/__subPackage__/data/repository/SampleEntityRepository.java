package ${groupId}.${subPackage}.data.repository;

import ${groupId}.${subPackage}.data.entity.SampleEntity;
import org.springframework.data.repository.CrudRepository;

public interface SampleEntityRepository extends CrudRepository<SampleEntity, Integer> {
}
