package service;

import entities.dao.ProjectDao;
import entities.dto.*;
import repository.ProjectRepository;
import service.converter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProjectService implements Crud<ProjectDto> {
    private final ProjectRepository repository;
    private final ProjectConverter converter;

    public ProjectService(ProjectRepository repository, ProjectConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public ProjectDto create(ProjectDto dto) {
        ProjectDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<ProjectDto> read(Integer id) {
        List<ProjectDao> daoList = repository.selectById(id);
        List<ProjectDto> dtoList = new ArrayList<>();
        for (ProjectDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<ProjectDto> read(String name) {
        List<ProjectDao> daoList = repository.selectByName(name);
        List<ProjectDto> dtoList = new ArrayList<>();
        for (ProjectDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public ProjectDto update(ProjectDto dto) {
        ProjectDao dao = repository.update(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<ProjectDto> allProjects() {
        List<ProjectDao> daoList = repository.selectAllProjects();
        List<ProjectDto> dtoList = new ArrayList<>();
        for (ProjectDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    public CustomerDto getProjectCustomer(Integer projectId) {
        return new CustomerConverter().from(repository.selectById(projectId).get(0).getCustomer());
    }

    public void setProjectCustomer(Integer projectId, CustomerDto customerDto){
        ProjectDao projectDao = repository.selectById(projectId).get(0);
        projectDao.setCustomer(new CustomerConverter().to(customerDto));
        repository.update(projectDao);
    }

    public CompanyDto getProjectCompany(Integer projectId) {
        return new CompanyConverter().from(repository.selectById(projectId).get(0).getCompany());
    }

    public void setProjectCompany(Integer projectId, CompanyDto companyDto){
        ProjectDao projectDao = repository.selectById(projectId).get(0);
        projectDao.setCompany(new CompanyConverter().to(companyDto));
        repository.update(projectDao);
    }

    public Set<DeveloperDto> getProjectDevelopers(Integer projectId) {
        Set<DeveloperDto> developerDtoSet = new DeveloperConverter().fromDaoSet(repository.selectById(projectId).get(0)
                .getDevelopers());
        if (developerDtoSet.isEmpty()) {
            throw new RuntimeException("No developers found!");
        }
        return developerDtoSet;
    }

    public void addProjectDeveloper(Integer projectId, DeveloperDto developerDto) {
        ProjectDao projectDao = repository.selectById(projectId).get(0);
        projectDao.addProjectDeveloper(new DeveloperConverter().to(developerDto));
        repository.update(projectDao);
    }

    public void removeProjectDeveloper(Integer projectId, DeveloperDto developerDto) {
        ProjectDao projectDao = repository.selectById(projectId).get(0);
        projectDao.removeProjectDeveloper(new DeveloperConverter().to(developerDto));
        repository.update(projectDao);
    }
}
