package org.csuf.cpsc411Hw1.Dao.Person

import org.csuf.cpsc411Hw1.Dao.Dao
import org.csuf.cpsc411Hw1.Dao.Database
import java.util.*

class ClaimDao : Dao() {

    fun addClaim(claimEntityObj : ClaimEntity){
        // First, get database connection
        val conn = Database.getInstance()?.getDBConnection()

        // Second, prepare the SQL statement
        sqlStmt = "Insert into claim (id, title, date, isSolved) values ('${claimEntityObj.id}', '${claimEntityObj.title}'," +
                " '${claimEntityObj.date}', '${claimEntityObj.isSolved}')"

        // Third, submit the SQL statement
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<ClaimEntity> {
        // First, get database connection
        val conn = Database.getInstance()?.getDBConnection()

        // Second, prepare the SQL statement
        sqlStmt = "select id, title, date, isSolved from claim"

        // Third, submit the SQL statement
        var claimEntityList : MutableList<ClaimEntity> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        // Fourth, convert into Kotlin object format
        while (st?.step()!!){
            //Convert each record into Claim object
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnNull(3)
            claimEntityList.add(ClaimEntity(UUID.fromString(id) , title, date, isSolved))
        }
        return claimEntityList
    }
}