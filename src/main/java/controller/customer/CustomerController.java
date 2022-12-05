package controller.customer;

import config.HibernateProvider;
import entities.dto.CustomerDto;
import entities.dto.ProjectDto;
import repository.CustomerRepository;
import repository.ProjectRepository;
import service.CustomerService;
import service.ProjectService;
import service.converter.CustomerConverter;
import service.converter.ProjectConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/customers")
public class CustomerController extends HttpServlet {
    private CustomerService service;
    private ProjectService projectService;
    private static final String CUSTOMER = "/WEB-INF/jsp/customer/";
    private static final String DELETE_URL = CUSTOMER + "deleteCustomerForm.jsp";
    private static final String CREATE_URL = CUSTOMER + "createCustomerForm.jsp";
    private static final String UPDATE_URL = CUSTOMER + "updateCustomerForm.jsp";
    private static final String FIND_URL = CUSTOMER + "findCustomer.jsp";
    private static final String PROJECTS_URL = CUSTOMER + "customerProjects.jsp";

    @Override
    public void init() {
        HibernateProvider dbProvider = new HibernateProvider();
        service = new CustomerService(
                new CustomerRepository(dbProvider),
                new CustomerConverter()
        );
        projectService = new ProjectService(
                new ProjectRepository(dbProvider),
                new ProjectConverter()
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
            }
        } else if (req.getParameterMap().containsKey("project")) {
            findProjects(req);
            req.getRequestDispatcher(PROJECTS_URL).forward(req, resp);
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
        } else if (req.getParameterMap().containsKey("project")) {
            switch (req.getParameter("project")) {
                case "add":
                    addProject(req);
                    req.getRequestDispatcher(PROJECTS_URL).forward(req, resp);
                    break;
                case "delete":
                    deleteProject(req);
                    req.getRequestDispatcher(PROJECTS_URL).forward(req, resp);
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
            req.setAttribute("message", String.format("Customer with id %d successfully deleted!", id));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        CustomerDto dto = new CustomerDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setName(req.getParameter("name"));
        dto.setEmail(req.getParameter("email"));
        try {
            service.read(dto.getId());
            dto = service.update(dto);
            req.setAttribute("message",
                    String.format("Customer with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        CustomerDto dto = new CustomerDto();
        dto.setName(req.getParameter("name"));
        dto.setEmail(req.getParameter("email"));
        try {
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Customer %s created with id %d", dto.getName(), dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByName(HttpServletRequest req) {
        List<CustomerDto> dtoList;
        String name = req.getParameter("name");
        try {
            dtoList = service.read(name);
            req.setAttribute("customers", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findById(HttpServletRequest req) {
        List<CustomerDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dtoList = service.read(id);
            req.setAttribute("customers", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findProjects(HttpServletRequest req) {
        Integer customerId = Integer.parseInt(req.getParameter("id"));
        try {
            req.setAttribute("customer_id", customerId);
            req.setAttribute("projects", service.getCustomerProjects(customerId));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addProject(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        Integer customerId = Integer.parseInt(req.getParameter("customer_id"));
        try {
            ProjectDto projectDto = projectService.read(projectId).get(0);
            service.addCustomerProject(customerId, projectDto);
            req.setAttribute("customer_id", customerId);
            req.setAttribute("projects", service.getCustomerProjects(customerId));
            req.setAttribute("message", "Project successfully added to customer");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void deleteProject(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        Integer customerId = Integer.parseInt(req.getParameter("customer_id"));
        try {
            ProjectDto projectDto = projectService.read(projectId).get(0);
            service.removeCustomerProject(customerId, projectDto);
            req.setAttribute("customer_id", customerId);
            req.setAttribute("projects", service.getCustomerProjects(customerId));
            req.setAttribute("message", "Project successfully deleted from customer");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }
}
