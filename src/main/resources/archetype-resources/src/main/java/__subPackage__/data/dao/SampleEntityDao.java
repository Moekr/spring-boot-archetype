package ${groupId}.${subPackage}.data.dao;

import ${groupId}.${subPackage}.data.entity.SampleEntity;
import ${groupId}.${subPackage}.data.repository.SampleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class SampleEntityDao extends AbstractDao<SampleEntity, Integer>{
    private final SampleEntityRepository repository;

    @Autowired
    public SampleEntityDao(SampleEntityRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void delete(SampleEntity entity){
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }
}
