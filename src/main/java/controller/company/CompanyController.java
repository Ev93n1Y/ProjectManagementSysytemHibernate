package controller.company;

import config.HibernateProvider;
import entities.dto.CompanyDto;
import entities.dto.DeveloperDto;
import entities.dto.ProjectDto;
import repository.CompanyRepository;
import repository.DeveloperRepository;
import repository.ProjectRepository;
import service.CompanyService;
import service.DeveloperService;
import service.ProjectService;
import service.converter.CompanyConverter;
import service.converter.DeveloperConverter;
import service.converter.ProjectConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/companies")
public class CompanyController extends HttpServlet {
    private CompanyService service;
    private DeveloperService developerService;
    private ProjectService projectService;
    private static final String COMPANY = "/WEB-INF/jsp/company/";
    private static final String DELETE_URL = COMPANY + "deleteCompanyForm.jsp";
    private static final String CREATE_URL = COMPANY + "createCompanyForm.jsp";
    private static final String UPDATE_URL = COMPANY + "updateCompanyForm.jsp";
    private static final String FIND_URL = COMPANY + "findCompany.jsp";
    private static final String DEVELOPERS_URL = COMPANY + "companyDevelopers.jsp";
    private static final String PROJECTS_URL = COMPANY + "companyProjects.jsp";

    @Override
    public void init() {
        HibernateProvider dbProvider = new HibernateProvider();
        service = new CompanyService(
                new CompanyRepository(dbProvider),
                new CompanyConverter()
        );
        developerService = new DeveloperService(
                new DeveloperRepository(dbProvider),
                new DeveloperConverter()
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
        } else if (req.getParameterMap().containsKey("developer")) {
            findDevelopers(req);
            req.getRequestDispatcher(DEVELOPERS_URL).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("dev")) {
            switch (req.getParameter("dev")) {
                case "add":
                    addDeveloper(req);
                    req.getRequestDispatcher(DEVELOPERS_URL).forward(req, resp);
                    break;
                case "delete":
                    deleteDeveloper(req);
                    req.getRequestDispatcher(DEVELOPERS_URL).forward(req, resp);
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
        } else if (req.getParameterMap().containsKey("method")) {
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
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        try {
            service.read(id);
            service.delete(id);
            req.setAttribute("message", String.format("Company with id %d successfully deleted!", id));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        CompanyDto dto = new CompanyDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setName(req.getParameter("name"));
        dto.setLocation(req.getParameter("location"));
        try {
            service.read(dto.getId());
            dto = service.update(dto);
            req.setAttribute("message",
                    String.format("Company with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        CompanyDto dto = new CompanyDto();
        dto.setName(req.getParameter("name"));
        dto.setLocation(req.getParameter("location"));
        try {
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Company %s created with id %d", dto.getName(), dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByName(HttpServletRequest req) {
        List<CompanyDto> dtoList;
        String name = req.getParameter("name");
        try {
            dtoList = service.read(name);
            req.setAttribute("companies", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findById(HttpServletRequest req) {
        List<CompanyDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dtoList = service.read(id);
            req.setAttribute("companies", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findDevelopers(HttpServletRequest req) {
        Integer companyId = Integer.parseInt(req.getParameter("id"));
        try {
            req.setAttribute("company_id", companyId);
            req.setAttribute("developers", service.getCompanyDevelopers(companyId));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addDeveloper(HttpServletRequest req) {
        Integer developerId = Integer.parseInt(req.getParameter("dev id"));
        Integer companyId = Integer.parseInt(req.getParameter("company_id"));
        try {
            DeveloperDto developerDto = developerService.read(developerId).get(0);
            service.addCompanyDeveloper(companyId, developerDto);
            req.setAttribute("company_id", companyId);
            req.setAttribute("developers", service.getCompanyDevelopers(companyId));
            req.setAttribute("message", "Developer successfully added to company");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void deleteDeveloper(HttpServletRequest req) {
        Integer developerId = Integer.parseInt(req.getParameter("dev id"));
        Integer companyId = Integer.parseInt(req.getParameter("company_id"));
        try {
            DeveloperDto developerDto = developerService.read(developerId).get(0);
            service.removeCompanyDeveloper(companyId, developerDto);
            req.setAttribute("company_id", companyId);
            req.setAttribute("developers", service.getCompanyDevelopers(companyId));
            req.setAttribute("message", "Developer successfully deleted from company");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findProjects(HttpServletRequest req) {
        Integer companyId = Integer.parseInt(req.getParameter("id"));
        try {
            req.setAttribute("company_id", companyId);
            req.setAttribute("projects", service.getCompanyProjects(companyId));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addProject(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        Integer companyId = Integer.parseInt(req.getParameter("company_id"));
        try {
            ProjectDto projectDto = projectService.read(projectId).get(0);
            service.addCompanyProject(companyId, projectDto);
            req.setAttribute("company_id", companyId);
            req.setAttribute("projects", service.getCompanyProjects(companyId));
            req.setAttribute("message", "Project successfully added to company");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void deleteProject(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        Integer companyId = Integer.parseInt(req.getParameter("company_id"));
        try {
            ProjectDto projectDto = projectService.read(projectId).get(0);
            service.removeCompanyProject(companyId, projectDto);
            req.setAttribute("company_id", companyId);
            req.setAttribute("projects", service.getCompanyProjects(companyId));
            req.setAttribute("message", "Project successfully deleted from company");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }
}
