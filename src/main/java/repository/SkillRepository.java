package repository;

import config.HibernateProvider;
import entities.dao.SkillDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SkillRepository implements Repository<SkillDao> {
    private final HibernateProvider provider;

    public SkillRepository(HibernateProvider provider) {
        this.provider = provider;
    }

    @Override
    public SkillDao save(SkillDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Skill not created! " + e.getMessage());
        }
    }

    @Override
    public List<SkillDao> selectById(Integer id) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<SkillDao> daoList = session.createQuery("from SkillDao where id = :id", SkillDao.class)
                    .setParameter("id", id)
                    .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Skill id %d not found! ", id));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<SkillDao> selectByName(String department) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<SkillDao> daoList = session.createQuery("from SkillDao where department = :department", SkillDao.class)
                    .setParameter("department", department)
                    .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Skill department %s not found! ", department));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public SkillDao update(SkillDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Skill id %d not updated! %s", dao.getId(), e.getMessage()));
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from SkillDao where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Skill id %d not deleted! %s", id, e.getMessage()));
        }
    }
}
