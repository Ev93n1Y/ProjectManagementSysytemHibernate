package service.converter;

import entities.dao.ProjectDao;
import entities.dto.ProjectDto;

import java.util.HashSet;
import java.util.Set;

public class ProjectConverter implements Convertable<ProjectDto, ProjectDao>{
    @Override
    public ProjectDto from(ProjectDao dao) {
        return new ProjectDto(
                dao.getId(),
                dao.getName(),
                dao.getCompany(),
                dao.getCustomer(),
                dao.getCost(),
                dao.getCreation_date()
        );
    }

    @Override
    public ProjectDao to(ProjectDto dto) {
        return new ProjectDao(
                dto.getId(),
                dto.getName(),
                dto.getCost(),
                dto.getCreation_date()
        );
    }
    public Set<ProjectDto> fromDaoSet(Set<ProjectDao> daoSet) {
        Set<ProjectDto> dtoSet = new HashSet<>();
        for (ProjectDao dao : daoSet) {
            dtoSet.add(from(dao));
        }
        return dtoSet;
    }

    public Set<ProjectDao> toDaoSet(Set<ProjectDto> dtoSet) {
        Set<ProjectDao> daoSet = new HashSet<>();
        for (ProjectDto dto : dtoSet) {
            daoSet.add(to(dto));
        }
        return daoSet;
    }
}
