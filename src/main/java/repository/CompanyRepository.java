package repository;

import config.HibernateProvider;
import entities.dao.CompanyDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;

public class CompanyRepository implements Repository<CompanyDao> {
    private final HibernateProvider provider;

    public CompanyRepository(HibernateProvider provider) {
        this.provider = provider;
    }

    @Override
    public CompanyDao save(CompanyDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Company not created! " + e.getMessage());
        }
    }

    @Override
    public List<CompanyDao> selectById(Integer id) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<CompanyDao> daoList =
                    session.createQuery("from CompanyDao where id = :id", CompanyDao.class)
                    .setParameter("id", id)
                            .getResultList();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Company id %d not found! ", id));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<CompanyDao> selectByName(String name) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<CompanyDao> daoList =
                    session.createQuery("from CompanyDao where name = :name", CompanyDao.class)
                            .setParameter("name", name)
                            .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Company name %s not found! ", name));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CompanyDao update(CompanyDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Company id %d not updated! %s", dao.getId(), e.getMessage()));
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from CompanyDao where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            String sqlEx = e.getCause().getCause().toString();
            throw new RuntimeException(String.format("Company id %d not deleted! %s", id,
                    sqlEx));
        }
    }
}
