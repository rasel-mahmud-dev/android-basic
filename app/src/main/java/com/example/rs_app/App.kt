import android.content.Context
import android.util.Log
import com.example.rs_app.GlobalAuthState
import com.example.rs_app.GlobalState
import com.example.rs_app.Message
import com.example.rs_app.utils.NotificationUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query


class App(private val context: Context) {
    private val firestore = FirebaseFirestore.getInstance()
    private val messagesCollection = firestore.collection("messages")
    private var listenerRegistration: ListenerRegistration? = null


    init {
        listenForMessages()
    }


    fun listenForMessages() {
        messagesCollection.orderBy("createdAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("FirestoreListener", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshots != null && !snapshots.isEmpty) {
                    for (doc in snapshots.documentChanges) {
                        val message = doc.document.toObject(Message::class.java)
                        if (message.email != GlobalAuthState.authUser?.email && !message.markAsNotified) {
                            onMessageReceived(message)
                            markAsNotified(message.id)
                        }
                    }
                }
            }

    }

    fun onMessageReceived(message: Message) {
        NotificationUtil.showNotification(context, message.text, message.text)
        GlobalState.messages = GlobalState.messages.plus(message)
    }

    fun markAsNotified(messageId: String) {
        println(messageId)
        if (messageId == "") return
        messagesCollection.document(messageId)
            .update("markAsNotified", true)
            .addOnSuccessListener {
                Log.d("FirestoreListener", "Message marked as notified.")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreListener", "Error marking message as notified.", e)
            }
    }

    fun clear() {
        listenerRegistration?.remove()
    }
}
