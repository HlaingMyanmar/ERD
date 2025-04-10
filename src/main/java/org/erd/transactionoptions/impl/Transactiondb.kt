package org.erd.transactionoptions.impl

import org.erd.dao.DataAccessObject
import org.erd.transactionoptions.models.Transactions
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class Transactiondb(val sessionFactory: SessionFactory) : DataAccessObject<Transactions> {



    override fun getAllData(): List<Transactions?>? {
        TODO("Not yet implemented")
    }

    override fun findDataById(id: Int): Transactions? {
        TODO("Not yet implemented")
    }

    override fun insertData(t: Transactions?): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateData(t: Transactions?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteData(t: Transactions?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteDataById(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    fun insertTransactionCapital(transaction: Transactions, session: Session): Boolean {
        return try {
            // Begin transaction
            val newSession = sessionFactory.openSession()

            val tx = newSession.beginTransaction()

            // Save the Transactions entity first
            newSession.persist(transaction)

            // If there's an associated CapitalInjections, save it too
            transaction.capitalInjection?.let { capitalInjection ->
                // Ensure the bidirectional relationship is set
                capitalInjection.transaction = transaction
                newSession.persist(capitalInjection)
            }

            // Commit the transaction
            tx.commit()
            true
        } catch (e: Exception) {
            // Rollback on error
            session.transaction?.rollback()
            println("Error inserting transaction and capital injection: ${e.message}") // Replace with proper logging
            false
        }
    }

}