package org.erd.capitaloptions.impls

import org.erd.capitaloptions.model.CapitalInjection
import org.erd.capitaloptions.views.CapitalInjectionDTO
import org.erd.dao.DataAccessObject
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class Capitaldb (
    private val sessionFactory: SessionFactory
): DataAccessObject<CapitalInjection>{


    fun getCaptitalViewModel(): List<CapitalInjectionDTO>{

        return try {
            val sql = """
                SELECT 
                    ci.injection_date AS capital_date,
                    t.reference_no AS transaction_code,
                    pm.method_name AS payment_method_name,
                    ci.amount,
                    ci.description
                FROM 
                    capital_injections ci
                    LEFT JOIN transactions t ON ci.transaction_id = t.transaction_id
                    LEFT JOIN transaction_payments tp ON t.transaction_id = tp.transaction_id
                    LEFT JOIN payment_methods pm ON tp.method_id = pm.method_id
            """.trimIndent()

            sessionFactory.openSession().use { session ->
                val query = session.createNativeQuery(sql)
                query.resultList.map { result ->
                    result as Array<*>
                    CapitalInjectionDTO(
                        capitalDate = result[0]?.let { it as? java.sql.Date }?.toLocalDate(),
                        transactionCode = result[1]?.toString(),
                        paymentMethodName = result[2]?.toString(),
                        amount = result[3]?.let { it as? Double },
                        description = result[4]?.toString()
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    }

    override fun getAllData(): List<CapitalInjection?>? {

        return try {


            val session = sessionFactory.openSession()
            val query =  session.createQuery(" From CapitalInjection", CapitalInjection::class.java)

            return query.resultList?:emptyList()

        }catch (e: Exception){
            return emptyList()
        }


    }

    override fun findDataById(id: Int): CapitalInjection? {
        TODO("Not yet implemented")
    }

    override fun insertData(t: CapitalInjection?): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateData(t: CapitalInjection?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteData(t: CapitalInjection?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteDataById(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}