package org.erd.paymentoptions.impl



import org.erd.dao.DataAccessObject
import org.erd.paymentoptions.model.Payment
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
open class PaymentDb @Autowired constructor(
    private val sessionFactory: SessionFactory
) : DataAccessObject<Payment> {

    override fun getAllData(): List<Payment> {

        try {
            val session = sessionFactory.openSession()
            val query = session.createQuery("FROM Payment", Payment::class.java)
            return query.resultList ?: emptyList()
        }
        catch (e: Exception) {
            return emptyList()
        }



    }

    override fun findDataById(id: Int): Payment? {
        val session = sessionFactory.openSession()
        return session.get(Payment::class.java, id)
    }

    override fun insertData(t: Payment?): Boolean {

        if (t == null) return false

        val session = sessionFactory.openSession()
        return try {
            val tx = session.beginTransaction()
            session.persist(t)
            tx.commit()
            true
        } catch (e: Exception) {
            session.transaction.rollback()
            e.printStackTrace()
            false
        } finally {
            session.close()
        }

    }

    override fun updateData(t: Payment?): Boolean {

        if(t==null) return false

        val session = sessionFactory.openSession()

        return try {

            val tx= session.beginTransaction()
            session.merge(t)
            tx.commit()
            true

        }catch (e: Exception){
            session.transaction.rollback()
            e.printStackTrace()
            false

        }finally {
            session.close()
        }



       return false
    }

    override fun deleteData(t: Payment?): Boolean {
        if(t==null) return false

        val session = sessionFactory.openSession()

        return try {

            val tx= session.beginTransaction()
            session.remove(t)
            tx.commit()
            true

        }catch (e: Exception){
            session.transaction.rollback()
            e.printStackTrace()
            false

        }finally {
            session.close()
        }



        return false
    }

    override fun deleteDataById(id: Int): Boolean {
        TODO("Not yet implemented")
    }


    fun deleteDataById(id: Int?): Boolean {
        val session = sessionFactory.openSession()
        return try {
            val tx = session.beginTransaction()
            val payment = session.get(Payment::class.java, id)

            if (payment != null) {
                session.remove(payment)
                tx.commit()
                true
            } else {
                tx.rollback()
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            session.transaction.rollback()
            false
        } finally {
            session.close()
        }
    }
}