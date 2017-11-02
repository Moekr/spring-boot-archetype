package ${groupId}.${subPackage}.data.dao;

import ${groupId}.${subPackage}.data.entity.SampleEntity;
import ${groupId}.${subPackage}.data.repository.SampleEntityRepository;
import ${groupId}.${subPackage}.util.ToolKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SampleEntityDao {
    private final SampleEntityRepository repository;

    @Autowired
    public SampleEntityDao(SampleEntityRepository repository) {
        this.repository = repository;
    }

    public SampleEntity save(SampleEntity entity){
        return repository.save(entity);
    }

    public List<SampleEntity> findAll(){
        return ToolKit.iterableToList(repository.findAll());
    }

    public SampleEntity findById(int entityId){
        return repository.findOne(entityId);
    }

    public SampleEntity delete(SampleEntity entity){
        entity.setDeletedAt(LocalDateTime.now());
        return repository.save(entity);
    }
}
