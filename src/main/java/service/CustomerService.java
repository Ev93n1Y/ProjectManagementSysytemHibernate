package service;

import entities.dao.CustomerDao;
import entities.dto.CustomerDto;
import entities.dto.ProjectDto;
import repository.CustomerRepository;
import service.converter.CustomerConverter;
import service.converter.ProjectConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomerService implements Crud<CustomerDto> {
    private final CustomerRepository repository;
    private final CustomerConverter converter;

    public CustomerService(CustomerRepository repository, CustomerConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public CustomerDto create(CustomerDto dto) {
        CustomerDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<CustomerDto> read(Integer id) {
        List<CustomerDao> daoList = repository.selectById(id);
        List<CustomerDto> dtoList = new ArrayList<>();
        for (CustomerDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<CustomerDto> read(String name) {
        List<CustomerDao> daoList = repository.selectByName(name);
        List<CustomerDto> dtoList = new ArrayList<>();
        for (CustomerDao dao : daoList) {
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public CustomerDto update(CustomerDto dto) {
        CustomerDao dao = repository.update(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Set<ProjectDto> getCustomerProjects(Integer customerId){
        Set<ProjectDto> projectDtoSet = new ProjectConverter().fromDaoSet(repository.selectById(customerId).get(0)
                .getCustomerProjects());
        if(projectDtoSet.isEmpty()){
            throw new RuntimeException("No projects found!");
        }
        return projectDtoSet;
    }

    public void addCustomerProject(Integer customerId, ProjectDto projectDto){
        CustomerDao customerDao = repository.selectById(customerId).get(0);
        customerDao.addCustomerProject(new ProjectConverter().to(projectDto));
        repository.update(customerDao);
    }

    public void removeCustomerProject(Integer customerId, ProjectDto projectDto){
        CustomerDao customerDao = repository.selectById(customerId).get(0);
        customerDao.removeCustomerProject(new ProjectConverter().to(projectDto));
        repository.update(customerDao);
    }
}
