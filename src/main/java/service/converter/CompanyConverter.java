package service.converter;

import entities.dao.CompanyDao;
import entities.dto.CompanyDto;

import java.util.HashSet;
import java.util.Set;

public class CompanyConverter implements Convertable<CompanyDto, CompanyDao> {
    @Override
    public CompanyDto from(CompanyDao dao) {
        return new CompanyDto(
                dao.getId(),
                dao.getName(),
                dao.getLocation()
        );
    }

    @Override
    public CompanyDao to(CompanyDto dto) {
        return new CompanyDao(
                dto.getId(),
                dto.getName(),
                dto.getLocation()
        );
    }

    public Set<CompanyDto> fromDaoSet(Set<CompanyDao> daoSet) {
        Set<CompanyDto> dtoSet = new HashSet<>();
        for (CompanyDao dao : daoSet) {
            dtoSet.add(from(dao));
        }
        return dtoSet;
    }

    public Set<CompanyDao> toDaoSet(Set<CompanyDto> dtoSet) {
        Set<CompanyDao> daoSet = new HashSet<>();
        for (CompanyDto dto : dtoSet) {
            daoSet.add(to(dto));
        }
        return daoSet;
    }
}
