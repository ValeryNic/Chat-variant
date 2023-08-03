import java.lang.RuntimeException
fun main(args: Array<String>) {
    MyChatService.sendMessage(1,MyMessage(0,1,"New message1"), "FriendOne")
    MyChatService.printChats()
    MyChatService.sendMessage(1,MyMessage(0,1,"New message2"), "FriendOne")
    MyChatService.sendMessage(2,MyMessage(0,2,"New message3"), "FriendTwo")
    MyChatService.sendMessage(2,MyMessage(0,2,"New message4"), "FriendTwo")
    MyChatService.printChats()
}
data class MyChat(
    var userId: Int,//Идентификатор участника чата
    var countNew: Int,//Количество непрочитанных сообщений
    var communicationName: String,//имя участника(название чата)
    //var newCommunication: Boolean,//Есть новые входящие сообщения
    var messages: MutableList<MyMessage> = mutableListOf()
)
data class MyMessage(
    var myMessageId: Int,
    //var chatId: Int,//Идентификатор чата
    var typeMessage: Int,//  1-отправленное, 2-полученние, 3-прочитанное, 4 - удаленное
    var text: String
)
class NoMessageException: RuntimeException("No message with &myMessageId")

class NoChatsException: RuntimeException("No chats with &chatsId")
object MyChatService {
    var chats: MutableMap <Int, MyChat> = mutableMapOf()
    //var messages: MutableMap <Int, MyMessage> = mutableMapOf()
    fun printChats() = println(chats)
    fun clear() {
        chats.clear()
        //messages.clear()
    }
    fun sendMessage(userId: Int, message: MyMessage,communicationName: String){//
//            if (chats.containsKey(userId)){
//                chats[userId]?.messages?.add(message.copy())
//            } else {
//                val chat = MyChat(chats.size,1,communicationName=communicationName, messages = mutableListOf())
//                chat.messages.add(message.copy())
//                chats[userId] = chat
//            }


        var chat = chats.getOrPut(userId){ MyChat(chats.size,1,communicationName=communicationName, messages = mutableListOf())}.messages.add(message.copy())
    }

    fun chatForRead(userId: Int) = chats[userId]//Получить чат пользователя

    fun deleteChat(userId: Int)= chats.remove(userId)//Удалить  чат пользователя

    fun createMyMessage(userId: Int, text: String): MyMessage = //Создать собственное сообщение
         MyChatService.chatForRead(userId)?.messages?.add(messages.size,1,text)


    fun lastMessages() = chats.values.map{chat -> chat.messages.lastOrNull{it.typeMessage==2}?.typeMessage=3 ?: throw NoMessageException() }
    fun messagesFromChat(userId: Int, count: Int): List<MyMessage>{//Взять 8 непрочитанных сообщений
        val chat = chats[userId]?: throw NoChatsException()
        return chat.messages.filter{it.typeMessage==2}.takeLast(count).onEach {it.typeMessage=3}
    }
    fun deleteMessage(userId: Int, myMessageId: Int) {
        for ((userId, chat) in chats)
    chat.messages[myMessageId].typeMessage = 4
    }
    fun getChats(){
        var chatsName: Array<String> = arrayOf()
        for ((index, chat) in chats) {
            chatsName += chats[index]?.communicationName?: throw NoChatsException()
        }
    }
    fun getUnreadChatsCount(): Int {
        var result: Int=0
        for ((index, chat) in chats) {
            if (chat.countNew > 0){
                result++
            }
        }
        return result
    }
}