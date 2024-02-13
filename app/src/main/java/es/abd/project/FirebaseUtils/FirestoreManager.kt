package es.abd.project.FirebaseUtils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import es.abd.project.ChatUtils.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp

class FirestoreManager {

    private val firestore by lazy { FirebaseFirestore.getInstance() }

    suspend fun addMessage(message: Message): Boolean {
        return try {

            firestore.collection(CHAT_COLLECTION).add(message).await()
            true
        }catch(e: Exception){
            false
        }
    }

    suspend fun getMessagesFlow(): Flow<List<Message>> = callbackFlow{
        // Collection Reference
        var notesCollection: CollectionReference? = null
        try {
            //Get the reference of note collection
            notesCollection = FirebaseFirestore.getInstance()
                .collection(CHAT_COLLECTION)

            // Registers callback to firestore, which will be called on collection updates
            val subscription = notesCollection?.orderBy(MSG_TIME,
                Query.Direction.ASCENDING)?.addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    // Format the results & send to the flow! Consumers will get the collection updated
                    val messages = mutableListOf<Message>()
                    snapshot.forEach{
                        messages.add(
                            Message(
                                name = it.get(MSG_USER).toString(),
                                text = it.get(MSG_TEXT).toString(),
                                time = it.get(MSG_TIME) as com.google.firebase.Timestamp
                            )
                        )
                    }
                    trySend(messages)
                }
            }


            awaitClose { subscription?.remove() }

        } catch (e: Throwable) {

            close(e)
        }
    }

    companion object{
        const val CHAT_COLLECTION = "Chat"
        const val MSG_TEXT = "text"
        const val MSG_USER = "name"
        const val MSG_TIME = "time"
    }

}