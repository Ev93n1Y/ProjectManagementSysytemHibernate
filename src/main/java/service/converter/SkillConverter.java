package service.converter;

import entities.dao.SkillDao;
import entities.dto.SkillDto;

import java.util.HashSet;
import java.util.Set;

public class SkillConverter implements Convertable<SkillDto, SkillDao>{
    @Override
    public SkillDto from(SkillDao dao) {
        return new SkillDto(
                dao.getId(),
                dao.getDepartment(),
                dao.getLevel()
        );
    }

    @Override
    public SkillDao to(SkillDto dto) {
        return new SkillDao(
                dto.getId(),
                dto.getDepartment(),
                dto.getLevel()
        );
    }

    public Set<SkillDto> fromDaoSet(Set<SkillDao> daoSet) {
        Set<SkillDto> dtoSet = new HashSet<>();
        for (SkillDao dao : daoSet) {
            dtoSet.add(from(dao));
        }
        return dtoSet;
    }

    public Set<SkillDao> toDaoSet(Set<SkillDto> dtoSet) {
        Set<SkillDao> daoSet = new HashSet<>();
        for (SkillDto dto : dtoSet) {
            daoSet.add(to(dto));
        }
        return daoSet;
    }
}
