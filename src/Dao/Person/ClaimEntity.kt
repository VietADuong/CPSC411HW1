package org.csuf.cpsc411Hw1.Dao.Person

import java.util.*

data class ClaimEntity (var id:UUID = UUID.randomUUID(), var title:String?
                        , var date:String?, var isSolved:Boolean = false)
