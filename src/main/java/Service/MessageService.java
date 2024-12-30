package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService 
{
    private MessageDAO messageDAO = new MessageDAO();

    // Method to validate message details
    private boolean isValidMessage(Message message)
    {
        return message.getMessage_text() != null && !message.getMessage_text().isBlank()
            && message.getMessage_text().length() <= 255;
    }

    // Method to create a new message
    public Message createMessage(Message message)
    {
        if(isValidMessage(message))
        {
            return messageDAO.creatMessage(message);
        }
        return null; // Return null for invalid message
    }

    // Method to retrieve all messages
    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    // Method to retrieve a message by ID
    public Message getMessageById(int id)
    {
        return messageDAO.getMessageById(id);
    }

    // Method to delete a message by ID
    public boolean deleteMessageById(int id)
    {
        return messageDAO.deleteMessageById(id);
    }

    // Method to update a message's text
    public Message updateMessageText(int id, String newMessageText)
    {
        if(newMessageText != null && !newMessageText.isBlank() && newMessageText.length() <= 255)
        {
            return messageDAO.updateMessageText(id, newMessageText);
        }
        return null; // Return null for invalid message text
    }

    // Method to retrieve all messages posted by a specific user
    public List<Message> getMessagesByUser(int userId)
    {
        return messageDAO.getMessagesByUser(userId);
    }

}
