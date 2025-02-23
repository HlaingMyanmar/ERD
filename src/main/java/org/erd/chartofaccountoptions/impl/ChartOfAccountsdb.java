package org.erd.chartofaccountoptions.impl;

import org.erd.chartofaccountoptions.model.ChartOfAccounts;
import org.erd.dao.DataAccessObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChartOfAccountsdb implements DataAccessObject<ChartOfAccounts> {


    private SessionFactory sessionFactory;

    public ChartOfAccountsdb(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ChartOfAccounts> getAllData() {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("from ChartOfAccounts").list();
        }


    }

    @Override
    public ChartOfAccounts findDataById(int id) {

        try (Session session = sessionFactory.openSession()) {
            return session.get(ChartOfAccounts.class, id);
        }



    }

    @Override
    public boolean insertData(ChartOfAccounts chartOfAccounts) {

        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx= session.beginTransaction();
            session.persist(chartOfAccounts);
            tx.commit();
            return true;
        }catch (Exception e) {
            tx.rollback();
            return false;
        }

    }

    @Override
    public boolean updateData(ChartOfAccounts chartOfAccounts) {

        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx= session.beginTransaction();
            session.update(chartOfAccounts);
            tx.commit();
            return true;
        }catch (Exception e) {
            tx.rollback();
        }

        return false;
    }

    @Override
    public boolean deleteData(ChartOfAccounts chartOfAccounts) {

        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx= session.beginTransaction();
            session.remove(chartOfAccounts);
            tx.commit();
            return true;
        }catch (Exception e) {
            tx.rollback();
            return false;
        }


    }

    @Override
    public boolean deleteDataById(int id) {

        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx= session.beginTransaction();
            ChartOfAccounts chartOfAccounts = session.get(ChartOfAccounts.class, id);
            session.delete(chartOfAccounts);
            tx.commit();
            return true;
        }
        catch (Exception e) {
            tx.rollback();
            return false;
        }

    }
}
