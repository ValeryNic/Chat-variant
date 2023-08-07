import org.junit.Test
import org.junit.Before
import org.junit.Assert.*

class ChatServiceTest {
    @Before
    fun clearBeforeTestChat() {
        MyChatService.clear()
    }

    @Test
    fun sendMessage1() {
        val message = MyMessage(0, 1, "Hello!")
        MyChatService.sendMessage(1, message, "Piter")
        val chat = MyChatService.chats[1]?.copy() ?: throw NoChatsException()
        val message1 = chat.messages[0].copy()
        assertEquals(message1, message)
    }
    @Test
    fun createMyMessage1(){
        val message = MyMessage(0, 1, "Hello!")
        MyChatService.sendMessage(1, message, "Piter")
        MyChatService.createMyMessage(1,"It`s good!")
        val chat = MyChatService.chats[1]?.copy() ?: throw NoChatsException()
        val message1 = chat.messages.last().copy()
        assertEquals("It`s good!",message1.text)
    }
    @Test
    fun messagesFromChat1(){
        val message = MyMessage(0, 2, "Hello!")
        val message1 = MyMessage(0, 2, "It`s good!")
        MyChatService.sendMessage(1, message, "Piter")
        MyChatService.sendMessage(1, message1, "Piter")
        MyChatService.messagesFromChat(1,1)
        val chat = MyChatService.chats[1]?.copy() ?: throw NoChatsException()
        val message2 = chat.messages.last().copy()
            println(chat.messages[0].typeMessage)
        println(chat.messages[1].typeMessage)
        val message3 = chat.messages[0].copy()
        assertEquals(2,message2.typeMessage)
        assertEquals(3,message3.typeMessage)
    }
}