package repository;

import config.HibernateProvider;
import entities.dao.DeveloperDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DeveloperRepository implements Repository<DeveloperDao> {
    private final HibernateProvider provider;

    public DeveloperRepository(HibernateProvider provider) {
        this.provider = provider;
    }

    @Override
    public DeveloperDao save(DeveloperDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Developer not created! " + e.getMessage());
        }
    }

    @Override
    public List<DeveloperDao> selectById(Integer id) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<DeveloperDao> daoList = session.createQuery("from DeveloperDao where id = :id", DeveloperDao.class)
                    .setParameter("id", id)
                    .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Developer id %d not found! ", id));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<DeveloperDao> selectByName(String name) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<DeveloperDao> daoList = session.createQuery("from DeveloperDao where name = :name", DeveloperDao.class)
                    .setParameter("name", name)
                    .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Developer name %s not found! ", name));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public DeveloperDao update(DeveloperDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Developer id %d not updated! %s", dao.getId(), e.getMessage()));
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from DeveloperDao where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Developer id %d not deleted! %s", id, e.getMessage()));
        }
    }

}
