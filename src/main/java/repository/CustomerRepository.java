package repository;

import config.HibernateProvider;
import entities.dao.CustomerDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerRepository implements Repository<CustomerDao> {
    private final HibernateProvider provider;

    public CustomerRepository(HibernateProvider provider) {
        this.provider = provider;
    }

    @Override
    public CustomerDao save(CustomerDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Customer not created! " + e.getMessage());
        }
    }

    //@Override
    public List<CustomerDao> selectById(Integer id) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<CustomerDao> daoList = session.createQuery("from CustomerDao where id = :id", CustomerDao.class)
                    .setParameter("id", id)
                    .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Customer id %d not found! ", id));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<CustomerDao> selectByName(String name) {
        try (final Session session = provider.openSession()) {
            session.beginTransaction();
            List<CustomerDao> daoList = session.createQuery("from CustomerDao where name = :name", CustomerDao.class)
                    .setParameter("name", name)
                    .list();
            if (daoList.isEmpty()) {
                throw new RuntimeException(String.format("Customer id %s not found! ", name));
            }
            return daoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CustomerDao update(CustomerDao dao) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(dao);
            transaction.commit();
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Customer id %d not updated! %s", dao.getId(), e.getMessage()));
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (final Session session = provider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from CustomerDao where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Customer id %d not deleted! %s", id, e.getMessage()));
        }
    }
}
