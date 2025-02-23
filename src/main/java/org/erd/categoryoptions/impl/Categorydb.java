package org.erd.categoryoptions.impl;


import org.erd.categoryoptions.model.Category;
import org.erd.dao.DataAccessObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Categorydb implements DataAccessObject<Category> {

    private final SessionFactory sessionFactory;

    public Categorydb(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private  Transaction tx = null;

    @Override
    public List<Category> getAllData() {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("from Category", Category.class).list();

        }


    }

    @Override
    public Category findDataById(int id) {


        try (Session session = sessionFactory.openSession()) {

            return session.get(Category.class, id);

        }




    }

    @Override
    public boolean insertData(Category category) {

        try (Session session = sessionFactory.openSession()) {


            tx = session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
            return true;


        }
        catch (Exception e) {

            tx.rollback();
            return false;

        }


    }

    @Override
    public boolean updateData(Category category) {

        try (Session session = sessionFactory.openSession()) {

            tx = session.beginTransaction();
            session.merge(category);
            session.getTransaction().commit();
            return true;

        }
        catch (Exception e) {

            tx.rollback();
            return false;
        }



    }

    @Override
    public boolean deleteData(Category category) {

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.remove(category);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e) {
            tx.rollback();
            return false;
        }



    }

    @Override
    public boolean deleteDataById(int id) {


        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Category category = session.get(Category.class, id);
            session.remove(category);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e) {

            tx.rollback();
            return false;
        }


    }
}
