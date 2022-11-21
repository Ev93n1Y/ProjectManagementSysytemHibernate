package service.converter;

import entities.dao.DeveloperDao;
import entities.dto.DeveloperDto;

import java.util.HashSet;
import java.util.Set;

public class DeveloperConverter implements Convertable<DeveloperDto, DeveloperDao>{
    @Override
    public DeveloperDto from(DeveloperDao dao) {
        return new DeveloperDto(
                dao.getId(),
                dao.getName(),
                dao.getAge(),
                dao.getGender(),
                dao.getSalary()
        );
    }

    @Override
    public DeveloperDao to(DeveloperDto dto) {
        return new DeveloperDao(
                dto.getId(),
                dto.getName(),
                dto.getAge(),
                dto.getGender(),
                dto.getSalary()
        );
    }

    public Set<DeveloperDto> fromDaoSet(Set<DeveloperDao> daoSet) {
        Set<DeveloperDto> dtoSet = new HashSet<>();
        for (DeveloperDao dao : daoSet) {
            dtoSet.add(from(dao));
        }
        return dtoSet;
    }

    public Set<DeveloperDao> toDaoSet(Set<DeveloperDto> dtoSet) {
        Set<DeveloperDao> daoSet = new HashSet<>();
        for (DeveloperDto dto : dtoSet) {
            daoSet.add(to(dto));
        }
        return daoSet;
    }
}
