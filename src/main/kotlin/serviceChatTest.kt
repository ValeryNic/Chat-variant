import org.junit.Test
import org.junit.Before
import org.junit.Assert.*
class MyChatServiceTest {
    var chats: MutableMap <Int, MyChat> = mutableMapOf()
    var messages: MutableMap <Int, MyMessage> = mutableMapOf()
    @Before
    fun clearBeforeTest(){
        MyChatService.clear()
    }
    @Test
    fun sendMessage1() {
        var message = MyMessage(1,2,"Hello!")
        var chatNew = MyChat(1, 1, "My friend", messages = mutableListOf(message))
        val chat = MyChatService.sendMessage(chatNew.userId,message,chatNew.communicationName)
        assertEquals(chat,chatNew)
    }
    fun chatForRead1(){
        var chatNew = MyChat(1, 1, "My friend", messages = mutableListOf(message))
        chats.put(1,chatNew)
        val chat = MyChatService.chatForRead(1)
        assertEquals(chat, chatNew)
    }
    fun createMyMessage1(){
        var message = MyMessage(1,1,"Hello!")
        var chatNew = MyChat(1, 1, "My friend", messages = mutableListOf(message))
        chats.put(1,chatNew)
        val chat = MyChatService.chatForRead(1)
        assertEquals(chat.messages.lastOrNull(), message)
    }
    fun deleteMessage1(){
        val message = MyMessage(1,2,"Hello!")
        val chatNew = MyChat(1, 1, "My friend", messages = mutableListOf(message))
        chats.put(1,chatNew)
        MyChatService.deleteMessage(1,1)
        val chat = MyChatService.chatForRead(1)
        val message1 = chat.messages[1]
        assertEquals(4,message1.typeMessage)
    }
}