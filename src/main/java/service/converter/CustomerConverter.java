package service.converter;

import entities.dao.CustomerDao;
import entities.dto.CustomerDto;

public class CustomerConverter implements Convertable<CustomerDto, CustomerDao>{
    @Override
    public CustomerDto from(CustomerDao dao) {
        return new CustomerDto(
                dao.getId(),
                dao.getName(),
                dao.getEmail()
        );
    }

    @Override
    public CustomerDao to(CustomerDto dto) {
        return new CustomerDao(
                dto.getId(),
                dto.getName(),
                dto.getEmail()
        );
    }
}
