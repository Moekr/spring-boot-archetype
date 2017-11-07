package ${groupId}.${subPackage}.logic.service;

import ${groupId}.${subPackage}.data.dao.SampleEntityDao;
import ${groupId}.${subPackage}.data.entity.SampleEntity;
import ${groupId}.${subPackage}.logic.vo.SampleEntityVo;
import ${groupId}.${subPackage}.util.ToolKit;
import ${groupId}.${subPackage}.web.dto.SampleEntityDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SampleEntityService {
    private final SampleEntityDao entityDao;

    @Autowired
    public SampleEntityService(SampleEntityDao entityDao) {
        this.entityDao = entityDao;
    }

    @Transactional
    public SampleEntityVo createSampleEntity(SampleEntityDto entityDto){
        SampleEntity entity = new SampleEntity();
        BeanUtils.copyProperties(entityDto, entity);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setModifiedAt(LocalDateTime.now());
        return new SampleEntityVo(entityDao.save(entity));
    }

    public List<SampleEntityVo> getSampleEntities(){
        return entityDao.findAll().stream().map(SampleEntityVo::new).collect(Collectors.toList());
    }

    public SampleEntityVo getSampleEntity(int entityId){
        SampleEntity entity = entityDao.findById(entityId);
        ToolKit.assertNotNull(entityId, entity);
        return new SampleEntityVo(entity);
    }

    @Transactional
    public SampleEntityVo updateSampleEntity(int entityId, SampleEntityDto entityDto){
        SampleEntity entity = entityDao.findById(entityId);
        ToolKit.assertNotNull(entityId, entity);
        BeanUtils.copyProperties(entityDto, entity);
        entity.setModifiedAt(LocalDateTime.now());
        return new SampleEntityVo(entityDao.save(entity));
    }

    @Transactional
    public void deleteSampleEntity(int entityId){
        SampleEntity entity = entityDao.findById(entityId);
        ToolKit.assertNotNull(entityId, entity);
        entityDao.delete(entity);
    }
}
