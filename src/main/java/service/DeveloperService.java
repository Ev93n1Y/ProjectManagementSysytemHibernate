package service;

import entities.dao.DeveloperDao;
import entities.dto.CompanyDto;
import entities.dto.DeveloperDto;
import entities.dto.ProjectDto;
import entities.dto.SkillDto;
import repository.DeveloperRepository;
import service.converter.CompanyConverter;
import service.converter.DeveloperConverter;
import service.converter.ProjectConverter;
import service.converter.SkillConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeveloperService implements Crud<DeveloperDto> {
    private final DeveloperRepository repository;
    private final DeveloperConverter converter;

    public DeveloperService(DeveloperRepository repository, DeveloperConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public DeveloperDto create(DeveloperDto dto) {
        DeveloperDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<DeveloperDto> read(Integer id) {
        List<DeveloperDao> daoList = repository.selectById(id);
        List<DeveloperDto> dtoList = new ArrayList<>();
        for (DeveloperDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<DeveloperDto> read(String name) {
        List<DeveloperDao> daoList = repository.selectByName(name);
        List<DeveloperDto> dtoList = new ArrayList<>();
        for (DeveloperDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public DeveloperDto update(DeveloperDto dto) {
        DeveloperDao dao = repository.update(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Integer totalDevelopersSalaryByProject(Integer id) {
        return repository.selectTotalSalaryByProjectId(id);
    }

    public List<DeveloperDto> allDevelopersByProject(Integer id) {
        List<DeveloperDao> daoList = repository.selectAllDevelopersByProject(id);
        List<DeveloperDto> dtoList = new ArrayList<>();
        for (DeveloperDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    public List<DeveloperDto> allJavaDevelopers() {
        List<DeveloperDao> daoList = repository.selectAllJavaDevelopers();
        List<DeveloperDto> dtoList = new ArrayList<>();
        for (DeveloperDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    public List<DeveloperDto> allMiddleDevelopers() {
        List<DeveloperDao> daoList = repository.selectAllMiddleDevelopers();
        List<DeveloperDto> dtoList = new ArrayList<>();
        for (DeveloperDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    public Set<ProjectDto> getDeveloperProjects(Integer developerId) {
        Set<ProjectDto> projectDtoSet = new ProjectConverter().fromDaoSet(repository.selectById(developerId).get(0)
                .getDeveloperProjects());
        if (projectDtoSet.isEmpty()) {
            throw new RuntimeException("No projects found!");
        }
        return projectDtoSet;
    }

    public void addDeveloperProject(Integer customerId, ProjectDto projectDto) {
        DeveloperDao developerDao = repository.selectById(customerId).get(0);
        developerDao.addDeveloperProject(new ProjectConverter().to(projectDto));
        repository.update(developerDao);
    }

    public void removeDeveloperProject(Integer customerId, ProjectDto projectDto) {
        DeveloperDao developerDao = repository.selectById(customerId).get(0);
        developerDao.removeDeveloperProject(new ProjectConverter().to(projectDto));
        repository.update(developerDao);
    }

    public Set<CompanyDto> getDeveloperCompanies(Integer developerId) {
        Set<CompanyDto> companyDtoSet = new CompanyConverter().fromDaoSet(repository.selectById(developerId).get(0)
                .getDeveloperCompanies());
        if (companyDtoSet.isEmpty()) {
            throw new RuntimeException("No companies found!");
        }
        return companyDtoSet;
    }

    public void addDeveloperCompany(Integer companyId, CompanyDto companyDto) {
        DeveloperDao developerDao = repository.selectById(companyId).get(0);
        developerDao.addDeveloperCompany(new CompanyConverter().to(companyDto));
        repository.update(developerDao);
    }

    public void removeDeveloperCompany(Integer companyId, CompanyDto companyDto) {
        DeveloperDao developerDao = repository.selectById(companyId).get(0);
        developerDao.removeDeveloperCompany(new CompanyConverter().to(companyDto));
        repository.update(developerDao);
    }

    public Set<SkillDto> getDeveloperSkills(Integer developerId){
        Set<SkillDto> skillDtoSet = new SkillConverter().fromDaoSet(repository.selectById(developerId).get(0)
                .getDeveloperSkills());
        if(skillDtoSet.isEmpty()){
            throw new RuntimeException("No skills found!");
        }
        return skillDtoSet;
    }

    public void addDeveloperSkills(Integer companyId, SkillDto skillDto){
        DeveloperDao developerDao = repository.selectById(companyId).get(0);
        developerDao.addDeveloperSkill(new SkillConverter().to(skillDto));
        repository.update(developerDao);
    }

    public void removeDeveloperSkills(Integer companyId, SkillDto skillDto){
        DeveloperDao developerDao = repository.selectById(companyId).get(0);
        developerDao.removeDeveloperSkill(new SkillConverter().to(skillDto));
        repository.update(developerDao);
    }
}
