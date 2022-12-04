package service;

import entities.dao.SkillDao;
import entities.dto.DeveloperDto;
import entities.dto.SkillDto;
import repository.SkillRepository;
import service.converter.DeveloperConverter;
import service.converter.SkillConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SkillService implements Crud<SkillDto> {
    private final SkillRepository repository;
    private final SkillConverter converter;

    public SkillService(SkillRepository repository, SkillConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public SkillDto create(SkillDto dto) {
        SkillDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<SkillDto> read(Integer id) {
        List<SkillDao> daoList = repository.selectById(id);
        List<SkillDto> dtoList = new ArrayList<>();
        for(SkillDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<SkillDto> read(String department) {
        List<SkillDao> daoList = repository.selectByName(department);
        List<SkillDto> dtoList = new ArrayList<>();
        for(SkillDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public SkillDto update(SkillDto dto) {
        SkillDao dao = repository.update(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Set<DeveloperDto> getSkillDevelopers(Integer skillId){
        return new DeveloperConverter().fromDaoSet(repository.selectById(skillId).get(0).getDevelopers());
    }

    public void addSkillDeveloper(Integer skillId, DeveloperDto developerDto){
        SkillDao skillDao = repository.selectById(skillId).get(0);
        skillDao.addDeveloper(new DeveloperConverter().to(developerDto));
        repository.update(skillDao);
    }

    public void removeSkillDeveloper(Integer skillId, DeveloperDto developerDto){
        SkillDao skillDao = repository.selectById(skillId).get(0);
        skillDao.removeDeveloper(new DeveloperConverter().to(developerDto));
        repository.update(skillDao);
    }
}
