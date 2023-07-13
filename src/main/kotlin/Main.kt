import jdk.javadoc.internal.doclint.Messages
import java.lang.RuntimeException

fun main(args: Array<String>) {
    MyChatService.sendMessage(1,MyMessage(0,1,0,"New message"))
    MyChatService.printChats()
    MyChatService.sendMessage(1,MyMessage(0,1,0,"New message"))
    MyChatService.sendMessage(1,MyMessage(0,1,0,"New message"))
    MyChatService.sendMessage(1,MyMessage(0,1,0,"New message"))
    MyChatService.printChats()
}
data class MyChat(
    var userId: Int,//Идентификатор участника чата
    var countNew: Int,//Количество непрочитанных сообщений
    var communicationName: String,//имя участника(название чата)
    //var newCommunication: Boolean,//Есть новые входящие сообщения
    //var messages = mutableListOf<MyMessage>()
)
data class MyMessage(
    var myMessageId: Int,
    var chatId: Int,//Идентификатор чата
    var typeMessage: Int,//  1-отправленное, 2-полученние, 3-прочитанное
    var text: String
)
class NoMessageException: RuntimeException("No message with &myMessageId")

class NoChatsException: RuntimeException("No chats with &chatsId")
object MyChatService {
    var chats: MutableMap <Int, MyChat> = mutableMapOf()
    var messages: MutableMap <Int, MyMessage> = mutableMapOf()
    fun printChats() = println(chats)
    fun sendMessage(userId: Int, message: MyMessage) {
        chats.getOrPut(userId) {
            message.myMessageId = messages.size
            message.chatId = userId
            message.typeMessage = 2
            messages[messages.size] = message
            MyChat(userId, 1, "new friend")}
            //var chat = chats[userId]?: throw NoChatException()
            //chat.newCommunication = true
            //chat.countNew += 1
            //chats[message.chatId] = chat.copy()
    }

    fun chatForRead(myChatId: Int) = chats[myChatId]//Получить чат пользователя

    fun deleteChat(myChatId: Int)= chats.remove(myChatId)//Удалить  чат пользователя

    fun createMyMessage(chatId: Int, text: String) = messages.put(messages.size, MyMessage(messages.size,chatId, 1,text))//Создать собственное сообщение


    fun messagesFromChat(myChatId: Int, count: Int){//} messages: MutableMap <Int, MyMessage>){
        //messages.filter {it:MyMessage(chateId=myChatId}.takeIf{it:MyMessage(chateId=myChatId,
        var countRead: Int=0
        while (count<countRead){
            for ((index,message) in messages){// in with(messages) {it.chatId=myChatId? ->
                var it: MyMessage = messages[index]?: throw NoMessageException()
                if (it.chatId==myChatId){
                    if (it.typeMessage==2){
                        it.typeMessage=3
                        countRead++
                    }
                }

            }
        }

    }
    fun deleteMessage(myMessageId: Int) = messages.remove(myMessageId)?: throw NoMessageException()//удалить сообщение

    fun getChats(){
        var chatsName: Array<String> = arrayOf()
        for ((index, chat) in chats) {
            chatsName += chats[index]?.communicationName?: throw NoChatsException()
        }
    }
    fun getUnreadChatcCount(): Int {
        var result: Int=0
        for ((index, chat) in chats) {
            if (chats[index].countNew > 0) {
                result++
            }
        }
        return result
    }
}