package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Assume database has a "message" table.
 * Similar values as Message class:
 * message_id, (int),
 * posted_by, (int),
 * message_text, (String) ~ Cannot be blank and must be under 255 characters ~,
 * time_posted_epoch, (Long)
 */

public class MessageDAO {
    
    /**
     * Create new message
     */

     public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
     }

     /**
      * Retrieve all messages from the messages table
      */

      public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
      }

      /**
       * Retrieve a message by message_id
       */

      public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * Delete a message given message_id
     */

     public Message deleteMessageById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Message deleted = null;
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql1 = "SELECT * FROM message WHERE message_id = ?;";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            if(rs.next()) {
                deleted = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));

                String sql2 = "DELETE FROM message WHERE message_id = ?;";
                preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                if(rs != null) rs.close();
                if(preparedStatement != null) preparedStatement.close();
                if(connection != null) connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return deleted;
     }

     /**
      * Update a message given message_id
      */

      public Message updateMessageById(int id, String newText) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, newText);
            preparedStatement.setInt(2, id);
            int textUpdated = preparedStatement.executeUpdate();

            if(textUpdated == 0) {
                return null;
            }

            Message updatedMessage = getMessageById(id);
            return updatedMessage;
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
      }

      /**
       * Get all messages given account_id
       */

       public List<Message> getAllMessagesByAccountId(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
       }


}