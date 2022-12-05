package controller.skill;

import config.HibernateProvider;
import entities.Department;
import entities.SkillLevel;
import entities.dto.DeveloperDto;
import entities.dto.SkillDto;
import repository.DeveloperRepository;
import repository.SkillRepository;
import service.DeveloperService;
import service.SkillService;
import service.converter.DeveloperConverter;
import service.converter.SkillConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/skills")
public class SkillController extends HttpServlet {
    private SkillService service;
    private DeveloperService developerService;
    private static final String SKILL = "/WEB-INF/jsp/skill/";
    private static final String DELETE_URL = SKILL + "deleteSkillForm.jsp";
    private static final String CREATE_URL = SKILL + "createSkillForm.jsp";
    private static final String UPDATE_URL = SKILL + "updateSkillForm.jsp";
    private static final String FIND_URL = SKILL + "findSkill.jsp";
    private static final String DEVELOPERS_URL = SKILL + "skillDevelopers.jsp";
    @Override
    public void init() {
        HibernateProvider dbProvider = new HibernateProvider();
        service = new SkillService(
                new SkillRepository(dbProvider),
                new SkillConverter()
        );
        developerService = new DeveloperService(
                new DeveloperRepository(dbProvider),
                new DeveloperConverter()
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
                case "find department":
                    findByDepartment(req);
                    req.getRequestDispatcher(FIND_URL).forward(req, resp);
                    break;
            }
        } else if(req.getParameterMap().containsKey("developer")){
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
        } else if(req.getParameterMap().containsKey("developer")){
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
            req.setAttribute("message", String.format("Skill with id %d successfully deleted!", id));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        SkillDto dto = new SkillDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setDepartment(Department.valueOf(req.getParameter("department")));
        dto.setLevel(SkillLevel.valueOf(req.getParameter("level")));
        try {
            service.read(dto.getId());
            dto = service.update(dto);
            req.setAttribute("message",
                    String.format("Skill with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        SkillDto dto = new SkillDto();
        dto.setDepartment(Department.valueOf(req.getParameter("department")));
        dto.setLevel(SkillLevel.valueOf(req.getParameter("level")));
        try {
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Skill %s %s created with id %d", dto.getDepartment(), dto.getLevel(), dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByDepartment(HttpServletRequest req) {
        List<SkillDto> dtoList;
        String department = req.getParameter("department");
        try {
            dtoList = service.read(department);
            req.setAttribute("skills", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }
    private void findById(HttpServletRequest req){
        List<SkillDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dtoList = service.read(id);
            req.setAttribute("skills", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findDevelopers(HttpServletRequest req) {
        Integer skillId = Integer.parseInt(req.getParameter("skill_id"));
        try {
            req.setAttribute("skill_id", skillId);
            req.setAttribute("developers", service.getSkillDevelopers(skillId));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void addDeveloper(HttpServletRequest req) {
        Integer skillId = Integer.parseInt(req.getParameter("skill_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            DeveloperDto developerDto = developerService.read(developerId).get(0);
            service.addSkillDeveloper(skillId, developerDto);
            req.setAttribute("skill_id", skillId);
            req.setAttribute("developers", service.getSkillDevelopers(skillId));
            req.setAttribute("message", "Developer successfully added to skill");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }

    private void deleteDeveloper(HttpServletRequest req) {
        Integer skillId = Integer.parseInt(req.getParameter("skill_id"));
        Integer developerId = Integer.parseInt(req.getParameter("developer_id"));
        try {
            DeveloperDto developerDto = developerService.read(developerId).get(0);
            service.removeSkillDeveloper(skillId, developerDto);
            req.setAttribute("skill_id", skillId);
            req.setAttribute("developers", service.getSkillDevelopers(skillId));
            req.setAttribute("message", "Developer successfully deleted from skill");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
        }
    }
}
