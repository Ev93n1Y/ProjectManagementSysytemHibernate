package controller.project;

import config.HibernateProvider;
import entities.dto.*;
import repository.CompanyRepository;
import repository.CustomerRepository;
import repository.DeveloperRepository;
import repository.ProjectRepository;
import service.CompanyService;
import service.CustomerService;
import service.DeveloperService;
import service.ProjectService;
import service.converter.CompanyConverter;
import service.converter.CustomerConverter;
import service.converter.DeveloperConverter;
import service.converter.ProjectConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = "/projects")
public class ProjectController extends HttpServlet {
    private ProjectService service;
    private DeveloperService developerService;
    private CustomerService customerService;
    private CompanyService companyService;
    private static final String PROJECT = "/WEB-INF/jsp/project/";
    private static final String DELETE_URL = PROJECT + "deleteProjectForm.jsp";
    private static final String CREATE_URL = PROJECT + "createProjectForm.jsp";
    private static final String UPDATE_URL = PROJECT + "updateProjectForm.jsp";
    private static final String FIND_URL = PROJECT + "findProject.jsp";
    private static final String FIND_ALL_URL = PROJECT + "findAllProjects.jsp";

    private static final String CUSTOMERS_URL = PROJECT + "projectCustomer.jsp";
    private static final String COMPANIES_URL = PROJECT + "projectCompany.jsp";
    private static final String DEVELOPERS_URL = PROJECT + "projectDevelopers.jsp";

    @Override
    public void init() {
        HibernateProvider dbProvider = new HibernateProvider();
        service = new ProjectService(
                new ProjectRepository(dbProvider),
                new ProjectConverter()
        );
        developerService = new DeveloperService(
                new DeveloperRepository(dbProvider),
                new DeveloperConverter()
        );
        customerService = new CustomerService(
                new CustomerRepository(dbProvider),
                new CustomerConverter()
        );
        companyService = new CompanyService(
                new CompanyRepository(dbProvider),
                new CompanyConverter()
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("method")) {
            switch (req.getParameter("method")) {
                case "find id":
                    findById(req);
                    req.getRequestDispatcher(FIND_URL).forward(req, resp);
                    break;
                case "find name":
                    findByName(req);
                    req.getRequestDispatcher(FIND_URL).forward(req, resp);
                    break;
                case "Find all projects":
                    findAllProjects(req);
                    req.getRequestDispatcher(FIND_ALL_URL).forward(req, resp);
                    break;
            }
        } else if (req.getParameterMap().containsKey("customer")) {
            findCustomer(req);
            req.getRequestDispatcher(CUSTOMERS_URL).forward(req, resp);
        } else if (req.getParameterMap().containsKey("company")) {
            findCompany(req);
            req.getRequestDispatcher(COMPANIES_URL).forward(req, resp);
        } else if (req.getParameterMap().containsKey("developer")) {
            findDevelopers(req);
            req.getRequestDispatcher(DEVELOPERS_URL).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("method")) {
            switch (req.getParameter("method")) {
                case "create":
                    create(req);
                    req.getRequestDispatcher(CREATE_URL).forward(req, resp);
                    break;
                case "update":
                    update(req);
                    req.getRequestDispatcher(UPDATE_URL).forward(req, resp);
                    break;
                case "delete":
                    doDelete(req, resp);
                    req.getRequestDispatcher(DELETE_URL).forward(req, resp);
                    break;
            }
        } else if (req.getParameterMap().containsKey("customer")) {
            addCustomer(req);
            req.getRequestDispatcher(CUSTOMERS_URL).forward(req, resp);
        } else if (req.getParameterMap().containsKey("company")) {
                addCompany(req);
                req.getRequestDispatcher(COMPANIES_URL).forward(req, resp);
        } else if (req.getParameterMap().containsKey("developer")) {
            switch (req.getParameter("developer")) {
                case "add":
                    addDeveloper(req);
                    req.getRequestDispatcher(DEVELOPERS_URL).forward(req, resp);
                    break;
                case "delete":
                    deleteDeveloper(req);
                    req.getRequestDispatcher(DEVELOPERS_URL).forward(req, resp);
                    break;
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        try {
            service.read(id);
            service.delete(id);
            req.setAttribute("message", String.format("Project with id %d successfully deleted!", id));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        ProjectDto dto = new ProjectDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setName(req.getParameter("name"));
        //dto.getCompany().setId(Integer.parseInt(req.getParameter("company id")));
        //dto.getCustomer().setId(Integer.parseInt(req.getParameter("customer id")));
        dto.setCost(Integer.parseInt(req.getParameter("cost")));
        dto.setCreation_date(Date.valueOf(req.getParameter("creation date")));
        try {
            service.read(dto.getId());
            dto = service.update(dto);
            req.setAttribute("message",
                    String.format("Project with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        ProjectDto dto = new ProjectDto();
        dto.setName(req.getParameter("name"));
        //dto.getCompany().setId(Integer.parseInt(req.getParameter("company id")));
        //dto.getCustomer().setId(Integer.parseInt(req.getParameter("customer id")));
        dto.setCost(Integer.parseInt(req.getParameter("cost")));
        dto.setCreation_date(Date.valueOf(req.getParameter("creation date")));
        try {
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Project %s created with id %d", dto.getName(), dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByName(HttpServletRequest req) {
        List<ProjectDto> dtoList;
        String name = req.getParameter("name");
        try {
            dtoList = service.read(name);
            req.setAttribute("projects", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findById(HttpServletRequest req) {
        List<ProjectDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dtoList = service.read(id);
            req.setAttribute("projects", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findAllProjects(HttpServletRequest req) {
        List<ProjectDto> dtoList;
        try {
            dtoList = service.allProjects();
            req.setAttribute("projects", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findCustomer(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        try {
            req.setAttribute("project_id", projectId);
            Set<CustomerDto> customerDtoSet = new HashSet<>();
            customerDtoSet.add(service.getProjectCustomer(projectId));
            req.setAttribute("customers", customerDtoSet);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addCustomer(HttpServletRequest req) {
        Integer customerId = Integer.parseInt(req.getParameter("customer_id"));
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        try {
            CustomerDto customerDto = customerService.read(customerId).get(0);
            service.setProjectCustomer(projectId, customerDto);
            req.setAttribute("project_id", projectId);
            Set<CustomerDto> customerDtoSet = new HashSet<>();
            customerDtoSet.add(service.getProjectCustomer(projectId));
            req.setAttribute("customers", customerDtoSet);
            req.setAttribute("message", "Customer successfully added to project");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void deleteCustomer(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        try {
            service.setProjectCustomer(projectId, new CustomerDto());
            req.setAttribute("project_id", projectId);
            Set<CustomerDto> customerDtoSet = new HashSet<>();
            req.setAttribute("customers", customerDtoSet);
            req.setAttribute("message", "Customer successfully deleted from project");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findCompany(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        try {
            req.setAttribute("project_id", projectId);
            Set<CompanyDto> companyDtoSet = new HashSet<>();
            companyDtoSet.add(service.getProjectCompany(projectId));
            req.setAttribute("companies", companyDtoSet);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addCompany(HttpServletRequest req) {
        Integer companyId = Integer.parseInt(req.getParameter("company_id"));
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        try {
            CompanyDto companyDto = companyService.read(companyId).get(0);
            service.setProjectCompany(projectId, companyDto);
            req.setAttribute("project_id", projectId);
            Set<CompanyDto> companyDtoSet = new HashSet<>();
            companyDtoSet.add(service.getProjectCompany(projectId));
            req.setAttribute("companies", companyDtoSet);
            req.setAttribute("message", "Company successfully added to project");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findDevelopers(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        try {
            req.setAttribute("project_id", projectId);
            req.setAttribute("developers", service.getProjectDevelopers(projectId));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addDeveloper(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            DeveloperDto developerDto = developerService.read(developerId).get(0);
            service.addProjectDeveloper(projectId, developerDto);
            req.setAttribute("project_id", projectId);
            req.setAttribute("developers", service.getProjectDevelopers(projectId));
            req.setAttribute("message", "Developer successfully added to project");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void deleteDeveloper(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            DeveloperDto developerDto = developerService.read(developerId).get(0);
            service.removeProjectDeveloper(projectId, developerDto);
            req.setAttribute("project_id", projectId);
            req.setAttribute("developers", service.getProjectDevelopers(projectId));
            req.setAttribute("message", "Developer successfully deleted from project");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }
}
