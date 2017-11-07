package ${groupId}.${subPackage}.data.dao;

import ${groupId}.${subPackage}.util.ToolKit;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.List;

abstract class AbstractDao<T, ID extends Serializable> {
    private final CrudRepository<T, ID> repository;

    AbstractDao(CrudRepository<T, ID> repository){
        this.repository = repository;
    }

    public T save(T entity){
        return repository.save(entity);
    }

    public List<T> findAll(){
        return ToolKit.iterableToList(repository.findAll());
    }

    public T findById(ID entityId){
        return repository.findOne(entityId);
    }

    public void delete(T entity){
        repository.delete(entity);
    }
}
