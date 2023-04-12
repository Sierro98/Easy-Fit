package ies.infantaelena.easy_fit_01.Recursos

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ies.infantaelena.easy_fit_01.data.Usuario

abstract class FirebaseDao {
    private lateinit var database: DatabaseReference
    fun initializeDbRef() {
        // [START initialize_database_ref]
        database = Firebase.database.reference
        // [END initialize_database_ref]
    }
    fun writeNewUser(userId: String, name: String, password: String) {
        val user = Usuario(name, password)
        database.child("user").child(userId).setValue(user)
    }

}