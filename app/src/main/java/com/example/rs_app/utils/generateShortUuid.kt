
import java.util.*

fun generateShortUuid(): String {
    val uuid = UUID.randomUUID().toString()
    // Extract a portion of the UUID string to form a short UUID
    return uuid.substring(0, 8)  // Adjust the substring length as needed
}
