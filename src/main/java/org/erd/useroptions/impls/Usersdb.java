package org.erd.useroptions.impls;

import org.erd.dao.DataAccessObject;
import org.erd.useroptions.models.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Usersdb implements DataAccessObject<Users> {

    private SessionFactory sessionFactory;

    public Usersdb(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Users> getAllData() {

        try(Session session = sessionFactory.openSession()) {

            return session.createQuery("from Users").list();
        }

    }

    @Override
    public Users findDataById(int id) {


        try(Session session = sessionFactory.openSession()) {
            return session.get(Users.class, id);
        }

    }

    @Override
    public boolean insertData(Users users) {

        Transaction tx = null;

        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(users);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {

            tx.rollback();
            return false;
        }

    }

    @Override
    public boolean updateData(Users users) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(users);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e) {
            tx.rollback();
            return false;
        }

    }

    @Override
    public boolean deleteData(Users users) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.remove(users);
            session.getTransaction().commit();
            return true;
        }catch (Exception e) {
            tx.rollback();
            return false;
        }
    }

    @Override
    public boolean deleteDataById(int id) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Users users = session.get(Users.class, id);
            session.remove(users);
            session.getTransaction().commit();
            return true;
        }catch (Exception e) {
            tx.rollback();
            return false;
        }

    }
}
