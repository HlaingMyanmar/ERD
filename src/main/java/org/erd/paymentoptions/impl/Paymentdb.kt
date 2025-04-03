package org.erd.paymentoptions.impl



import org.erd.dao.DataAccessObject
import org.erd.paymentoptions.model.Payment
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
open class PaymentDb @Autowired constructor(
    private val sessionFactory: SessionFactory
) : DataAccessObject<Payment> {

    override fun getAllData(): List<Payment> {
        val session = sessionFactory.openSession()
        val query = session.createQuery("FROM Payment", Payment::class.java)
        return query.resultList ?: emptyList()
    }

    override fun findDataById(id: Int): Payment? {
        val session = sessionFactory.openSession()
        return session.get(Payment::class.java, id)
    }

    override fun insertData(t: Payment?): Boolean {

        println(t?.method_id)
        if (t == null) return false
        val session = sessionFactory.openSession()
        return try {
            session.persist(t)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun updateData(t: Payment?): Boolean {
        if (t == null) return false
        val session = sessionFactory.currentSession
        return try {
            session.merge(t)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun deleteData(t: Payment?): Boolean {
        if (t == null) return false
        val session = sessionFactory.currentSession
        return try {
            session.delete(t)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun deleteDataById(id: Int): Boolean {
        val session = sessionFactory.currentSession
        val payment = findDataById(id) ?: return false
        return try {
            session.delete(payment)
            true
        } catch (e: Exception) {
            false
        }
    }
}