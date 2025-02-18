package org.erd.roleoptions.impls;


import org.erd.dao.DataAccessObject;
import org.erd.roleoptions.models.Roles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class Rolesdb implements DataAccessObject<Roles> {

    private SessionFactory sessionFactory;

   public Rolesdb(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Roles> getAllData() {

       try (Session session = sessionFactory.openSession()) {
           return session.createQuery("from Roles", Roles.class).list();
       }
    }

    @Override
    public Roles findDataById(int id) {

       try (Session session = sessionFactory.openSession()) {
           return session.get(Roles.class, id);

       }catch (Exception e) {
           return null;
       }
    }

    @Override

    public boolean insertData(Roles roles) {
        Transaction tx = null;
        try(Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            session.persist(roles);
            session.getTransaction().commit();
            return true;


        }
        catch(Exception e) {

            tx.rollback();
            return false;

        }

    }

    @Override
    public boolean updateData(Roles roles) {
       Transaction tx = null;

       try(Session session = sessionFactory.getCurrentSession()) {

           tx = session.beginTransaction();
           session.merge(roles);
           session.getTransaction().commit();
           return true;

       }catch (Exception e) {
           tx.rollback();
           return false;
       }


       }

    @Override
    public boolean deleteData(Roles roles) {

       Transaction tx = null;
       try(Session session = sessionFactory.getCurrentSession()) {

           tx = session.beginTransaction();
           session.remove(roles);
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

        try(Session session = sessionFactory.getCurrentSession()) {
            Roles roles = session.get(Roles.class, id);
            tx = session.beginTransaction();
            session.remove(roles);
            session.getTransaction().commit();
            return true;

        }catch (Exception e) {
            tx.rollback();
        }
        return false;
    }
}
