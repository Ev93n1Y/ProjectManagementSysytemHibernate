package controller.developer;

import config.HibernateProvider;
import entities.Gender;
import entities.dto.CompanyDto;
import entities.dto.DeveloperDto;
import entities.dto.ProjectDto;
import entities.dto.SkillDto;
import repository.CompanyRepository;
import repository.DeveloperRepository;
import repository.ProjectRepository;
import repository.SkillRepository;
import service.CompanyService;
import service.DeveloperService;
import service.ProjectService;
import service.SkillService;
import service.converter.CompanyConverter;
import service.converter.DeveloperConverter;
import service.converter.ProjectConverter;
import service.converter.SkillConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = "/developers")
public class DeveloperController extends HttpServlet {
    private DeveloperService service;
    private ProjectService projectService;
    private CompanyService companyService;
    private SkillService skillService;
    private static final String DEVELOPER = "/WEB-INF/jsp/developer/";
    private static final String DELETE_URL = DEVELOPER + "deleteDeveloperForm.jsp";
    private static final String CREATE_URL = DEVELOPER + "createDeveloperForm.jsp";
    private static final String UPDATE_URL = DEVELOPER + "updateDeveloperForm.jsp";
    private static final String FIND_URL = DEVELOPER + "findDeveloper.jsp";
    private static final String PROJECT_TOTAL_SALARY_URL = DEVELOPER + "totalProjectDevelopersSalary.jsp";
    private static final String PROJECT_DEVELOPERS_URL = DEVELOPER + "findDevelopersOnProject.jsp";
    private static final String JAVA_DEVELOPERS_URL = DEVELOPER + "findJavaDevelopers.jsp";
    private static final String MIDDLE_DEVELOPERS_URL = DEVELOPER + "findMiddleDevelopers.jsp";
    private static final String PROJECTS_URL = DEVELOPER + "developerProjects.jsp";
    private static final String COMPANIES_URL = DEVELOPER + "developerCompanies.jsp";
    private static final String SKILLS_URL = DEVELOPER + "developerSkills.jsp";

    @Override
    public void init() {
        HibernateProvider dbProvider = new HibernateProvider();
        service = new DeveloperService(
                new DeveloperRepository(dbProvider),
                new DeveloperConverter()
        );
        projectService = new ProjectService(
                new ProjectRepository(dbProvider),
                new ProjectConverter()
        );
        companyService = new CompanyService(
                new CompanyRepository(dbProvider),
                new CompanyConverter()
        );
        skillService = new SkillService(
                new SkillRepository(dbProvider),
                new SkillConverter()
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
                case "find devs by project id":
                    findDevelopersOnProject(req);
                    req.getRequestDispatcher(PROJECT_DEVELOPERS_URL).forward(req, resp);
                    break;
                case "Find java devs":
                    findAllJavaDevelopers(req);
                    req.getRequestDispatcher(JAVA_DEVELOPERS_URL).forward(req, resp);
                    break;
                case "Find middle devs":
                    findAllMiddleDevelopers(req);
                    req.getRequestDispatcher(MIDDLE_DEVELOPERS_URL).forward(req, resp);
                    break;
                case "salary":
                    totalDevelopersSalaryAtProject(req);
                    req.getRequestDispatcher(PROJECT_TOTAL_SALARY_URL).forward(req, resp);
                    break;
            }
        } else if (req.getParameterMap().containsKey("project")) {
            findProjects(req);
            req.getRequestDispatcher(PROJECTS_URL).forward(req, resp);
        } else if (req.getParameterMap().containsKey("company")) {
            findCompanies(req);
            req.getRequestDispatcher(COMPANIES_URL).forward(req, resp);
        } else if (req.getParameterMap().containsKey("skill")) {
            findSkills(req);
            req.getRequestDispatcher(SKILLS_URL).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("project")) {
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
        } else if (req.getParameterMap().containsKey("company")) {
            switch (req.getParameter("company")) {
                case "add":
                    addCompany(req);
                    req.getRequestDispatcher(COMPANIES_URL).forward(req, resp);
                    break;
                case "delete":
                    deleteCompany(req);
                    req.getRequestDispatcher(COMPANIES_URL).forward(req, resp);
                    break;
            }
        } else if (req.getParameterMap().containsKey("skill")) {
            switch (req.getParameter("skill")) {
                case "add":
                    addSkill(req);
                    req.getRequestDispatcher(SKILLS_URL).forward(req, resp);
                    break;
                case "delete":
                    deleteSkill(req);
                    req.getRequestDispatcher(SKILLS_URL).forward(req, resp);
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
            req.setAttribute("message", String.format("Developer with id %d successfully deleted!", id));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        DeveloperDto dto = new DeveloperDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setName(req.getParameter("name"));
        dto.setAge(Integer.parseInt(req.getParameter("age")));
        dto.setGender(Gender.valueOf(req.getParameter("gender")));
        dto.setSalary(Integer.parseInt(req.getParameter("salary")));
        try {
            service.read(dto.getId());
            dto = service.update(dto);
            req.setAttribute("message",
                    String.format("Developer with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        DeveloperDto dto = new DeveloperDto();
        dto.setName(req.getParameter("name"));
        dto.setAge(Integer.parseInt(req.getParameter("age")));
        dto.setGender(Gender.valueOf(req.getParameter("gender")));
        dto.setSalary(Integer.parseInt(req.getParameter("salary")));
        try {
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Developer %s created with id %d", dto.getName(), dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByName(HttpServletRequest req) {
        List<DeveloperDto> dtoList;
        String name = req.getParameter("name");
        try {
            dtoList = service.read(name);
            req.setAttribute("developers", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findById(HttpServletRequest req) {
        List<DeveloperDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dtoList = service.read(id);
            req.setAttribute("developers", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findDevelopersOnProject(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        List<DeveloperDto> dtoList = service.allDevelopersByProject(id);
        if (dtoList.isEmpty()) {
            req.setAttribute("message", "There is no developers by specified project id");
        } else {
            req.setAttribute("developers", dtoList);
        }
    }

    private void findAllJavaDevelopers(HttpServletRequest req) {
        List<DeveloperDto> dtoList = service.allJavaDevelopers();
        if (dtoList.isEmpty()) {
            req.setAttribute("message", "There is no Java developers");
        } else {
            req.setAttribute("developers", dtoList);
        }
    }

    private void findAllMiddleDevelopers(HttpServletRequest req) {
        List<DeveloperDto> dtoList = service.allMiddleDevelopers();
        if (dtoList.isEmpty()) {
            req.setAttribute("message", "There is no middle developers");
        } else {
            req.setAttribute("developers", dtoList);
        }
    }

    private void totalDevelopersSalaryAtProject(HttpServletRequest req) {
        int totalSalary = service.totalDevelopersSalaryByProject(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("id", req.getParameter("id"));
        req.setAttribute("salary", totalSalary);
    }

    private void findProjects(HttpServletRequest req) {
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            req.setAttribute("developer_id", developerId);
            req.setAttribute("projects", service.getDeveloperProjects(developerId));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addProject(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            ProjectDto projectDto = projectService.read(projectId).get(0);
            service.addDeveloperProject(developerId, projectDto);
            req.setAttribute("developer_id", developerId);
            req.setAttribute("projects", service.getDeveloperProjects(developerId));
            req.setAttribute("message", "Project successfully added to developer");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void deleteProject(HttpServletRequest req) {
        Integer projectId = Integer.parseInt(req.getParameter("project_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            ProjectDto projectDto = projectService.read(projectId).get(0);
            service.removeDeveloperProject(developerId, projectDto);
            req.setAttribute("developer_id", developerId);
            req.setAttribute("projects", service.getDeveloperProjects(developerId));
            req.setAttribute("message", "Project successfully deleted from developer");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findCompanies(HttpServletRequest req) {
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            req.setAttribute("developer_id", developerId);
            req.setAttribute("companies", service.getDeveloperCompanies(developerId));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addCompany(HttpServletRequest req) {
        Integer companyId = Integer.parseInt(req.getParameter("company_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            CompanyDto companyDto = companyService.read(companyId).get(0);
            service.addDeveloperCompany(developerId, companyDto);
            req.setAttribute("developer_id", developerId);
            req.setAttribute("companies", service.getDeveloperCompanies(developerId));
            req.setAttribute("message", "Company successfully added to developer");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void deleteCompany(HttpServletRequest req) {
        Integer companyId = Integer.parseInt(req.getParameter("company_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            CompanyDto companyDto = companyService.read(companyId).get(0);
            service.removeDeveloperCompany(developerId, companyDto);
            req.setAttribute("developer_id", developerId);
            req.setAttribute("companies", service.getDeveloperCompanies(developerId));
            req.setAttribute("message", "Company successfully deleted from developer");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findSkills(HttpServletRequest req) {
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            req.setAttribute("developer_id", developerId);
            req.setAttribute("skills", service.getDeveloperSkills(developerId));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addSkill(HttpServletRequest req) {
        Integer skillId = Integer.parseInt(req.getParameter("skill_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            SkillDto skillDto = skillService.read(skillId).get(0);
            service.addDeveloperSkills(developerId, skillDto);
            req.setAttribute("developer_id", developerId);
            req.setAttribute("skills", service.getDeveloperSkills(developerId));
            req.setAttribute("message", "Skill successfully added to developer");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void deleteSkill(HttpServletRequest req) {
        Integer skillId = Integer.parseInt(req.getParameter("skill_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            SkillDto skillDto = skillService.read(skillId).get(0);
            service.removeDeveloperSkills(developerId, skillDto);
            req.setAttribute("developer_id", developerId);
            req.setAttribute("skills", service.getDeveloperSkills(developerId));
            req.setAttribute("message", "Skill successfully deleted from developer");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }
}
