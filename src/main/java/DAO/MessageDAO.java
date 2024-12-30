package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO 
{
    // Method to create a new message in the database
    public Message creatMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            //SQL code
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0)
            {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next())
                {
                    message.setMessage_id(generatedKeys.getInt(1));
                }
                return message;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve all messages from the database
    public List<Message> getAllMessages()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        
        try
        {
            //SQL code
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        
        return messages;
    }

    //Method to retrieve a message by its ID
    public Message getMessageById(int id)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            //SQL code
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) 
            {
                return new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    // Method to delete a message by its ID
    public boolean deleteMessageById(int id)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            //SQL code
            String sql = "DELETE FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }

    //Method to update a message text by its ID
    public Message updateMessageText(int id, String newMessageText)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, newMessageText);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0)
            {
                return getMessageById(id);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    //Method to retrieve messages posted by a specific user
    public List<Message> getMessagesByUser(int userId)
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try
        {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) 
            {
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
                messages.add(message);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;
    }
}
