package repository;

import config.HibernateProvider;
import entities.dao.ProjectDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class ProjectRepository implements Repository<ProjectDao> {
    private final HibernateProvider provider;

    public ProjectRepository(HibernateProvider provider) {
        this.provider = provider;
    }

    @Override
    public ProjectDao save(ProjectDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Project not created! " + e.getMessage());
        }
    }

    @Override
    public List<ProjectDao> selectById(Integer id) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<ProjectDao> daoList = session.createQuery("from ProjectDao where id = :id", ProjectDao.class)
                    .setParameter("id", id)
                    .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Project id %d not found! ", id));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ProjectDao> selectByName(String name) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<ProjectDao> daoList = session.createQuery("from ProjectDao where name = :name",
                            ProjectDao.class)
                    .setParameter("name", name)
                    .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Project name %s not found! ", name));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProjectDao update(ProjectDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Project id %d not updated! %s", dao.getId(), e.getMessage()));
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from ProjectDao where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Project id %d not deleted! %s", id, e.getMessage()));
        }
    }

    public List<ProjectDao> selectAllProjects() {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<ProjectDao> daoList = session.createQuery("from ProjectDao", ProjectDao.class)
                    .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException("No Project found! ");
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
