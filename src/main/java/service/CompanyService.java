package service;

import entities.dao.CompanyDao;
import entities.dto.CompanyDto;
import entities.dto.DeveloperDto;
import entities.dto.ProjectDto;
import repository.CompanyRepository;
import service.converter.CompanyConverter;
import service.converter.DeveloperConverter;
import service.converter.ProjectConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompanyService implements Crud<CompanyDto> {
    private final CompanyRepository repository;
    private final CompanyConverter converter;

    public CompanyService(CompanyRepository repository, CompanyConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public CompanyDto create(CompanyDto dto) {
        CompanyDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<CompanyDto> read(Integer id) {
        List<CompanyDao> daoList = repository.selectById(id);
        List<CompanyDto> dtoList = new ArrayList<>();
        for (CompanyDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<CompanyDto> read(String name) {
        List<CompanyDao> daoList = repository.selectByName(name);
        List<CompanyDto> dtoList = new ArrayList<>();
        for (CompanyDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public CompanyDto update(CompanyDto dto) {
        CompanyDao dao = repository.update(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Set<DeveloperDto> getCompanyDevelopers(Integer companyId){
        Set<DeveloperDto> developerDtoSet = new DeveloperConverter().fromDaoSet(repository.selectById(companyId).get(0)
                .getCompanyDevelopers());
        if(developerDtoSet.isEmpty()){
            throw new RuntimeException("No developers found!");
        }
        return developerDtoSet;
    }

    public void addCompanyDeveloper(Integer companyId, DeveloperDto developerDto){
        CompanyDao companyDao = repository.selectById(companyId).get(0);
        companyDao.addCompanyDeveloper(new DeveloperConverter().to(developerDto));
        repository.update(companyDao);
    }

    public void removeCompanyDeveloper(Integer companyId, DeveloperDto developerDto){
        CompanyDao companyDao = repository.selectById(companyId).get(0);
        companyDao.removeCompanyDeveloper(new DeveloperConverter().to(developerDto));
        repository.update(companyDao);
    }

    public Set<ProjectDto> getCompanyProjects(Integer companyId){
        Set<ProjectDto> projectDtoSet = new ProjectConverter().fromDaoSet(repository.selectById(companyId).get(0)
                .getCompanyProjects());
        if(projectDtoSet.isEmpty()){
            throw new RuntimeException("No projects found!");
        }
        return projectDtoSet;
    }

    public void addCompanyProject(Integer companyId, ProjectDto projectDto){
        CompanyDao companyDao = repository.selectById(companyId).get(0);
        companyDao.addCompanyProject(new ProjectConverter().to(projectDto));
        System.out.println(repository.update(companyDao).getCompanyProjects());
    }

    public void removeCompanyProject(Integer companyId, ProjectDto projectDto){
        CompanyDao companyDao = repository.selectById(companyId).get(0);
        companyDao.removeCompanyProject(new ProjectConverter().to(projectDto));
        System.out.println(repository.update(companyDao).getCompanyProjects());
    }
}
