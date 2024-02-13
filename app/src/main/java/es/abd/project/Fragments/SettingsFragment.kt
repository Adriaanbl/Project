package es.abd.project.Fragments

import android.content.Context
import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.firestore.FirebaseFirestore
import es.abd.project.FirebaseUtils.AuthManager
import es.abd.project.R


class SettingsFragment : PreferenceFragmentCompat() {

    private val authManager: AuthManager by lazy { AuthManager() }
    private val user = authManager.getCurrentUser()


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val usernamePreference = findPreference<EditTextPreference>("username")

        usernamePreference?.setOnPreferenceChangeListener { preference, newValue ->
            // Obtener el nuevo valor del EditTextPreference
            val newName = newValue.toString()

            emailUpdate(newName)
            usernameChange(newName)
            true
        }
    }

    fun usernameChange(name: String){

        val prefs = requireContext().getSharedPreferences("es.abd.project_preferences", Context.MODE_PRIVATE)
        val currentUser = prefs.getString("username","")

        val db = FirebaseFirestore.getInstance()

        db.collection("Chat")
            .whereEqualTo("name", currentUser)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Obtén la lista de documentos que cumplen con el criterio de búsqueda
                    val documents = task.result

                    // Itera sobre los documentos y actualiza el campo "name"
                    for (document in documents!!) {
                        // Actualiza el campo "name" del documento actual
                        db.collection("Chat")
                            .document(document.id)
                            .update("name",name )
                            .addOnSuccessListener {
                                // Actualización exitosa
                                println("Campo 'name' actualizado exitosamente")
                            }
                            .addOnFailureListener { e ->
                                // Error al actualizar el campo "name" del documento
                                println("Error al actualizar el campo 'name' del documento: $e")
                            }
                    }
                } else {
                    // Error al obtener los documentos
                    println("Error al obtener los documentos: ${task.exception}")
                }
            }

    }

    private fun emailUpdate(nuevoNombre: String) {

        user?.verifyBeforeUpdateEmail(nuevoNombre)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Actualización exitosa en Firebase Auth
                    println("Correo electrónico actualizado exitosamente en Firebase Auth")
                } else {
                    // Error al actualizar el correo electrónico en Firebase Auth
                    println("Error al actualizar el correo electrónico en Firebase Auth: ${task.exception}")
                }
            }
    }

}