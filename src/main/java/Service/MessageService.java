package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message){
        Message newMessage = messageDAO.createMessage(message);
        return newMessage;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }

    public Message getMessageById(int message_id){
        Message result = messageDAO.getMessageById(message_id);
        return result;
    }

    
    public Message deleteMessageById(int message_id){
        Message originalMessage = messageDAO.getMessageById(message_id);
        if(originalMessage != null) {
            messageDAO.deleteMessageById(message_id);
            return originalMessage;
        }
        return null;
    }
     

    public Message updateMessageById(int message_id, String newText){
        Message identify = messageDAO.getMessageById(message_id);
        Message update = messageDAO.updateMessageById(message_id, newText);
        if(identify == null){
            return null;
        } else {
            return update;
        }
        
    }
    
    public List<Message> getAllMessagesByUser(int account_id){
        List<Message> messages = messageDAO.getAllMessagesByAccountId(account_id);
        return messages;
    }
    }
