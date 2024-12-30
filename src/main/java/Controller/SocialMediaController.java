package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController 
{
    
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() 
    {
        Javalin app = Javalin.create();

        //Account endpoints
        app.post("/register", this::registerAccount);
        app.post("/login", this::loginAccount);

        //Message endpoints
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageText);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    
    
    //Handlers for accounts
    private void registerAccount(Context ctx) 
    {
        Account account = ctx.bodyAsClass(Account.class);
        Account createdAccount = accountService.registerAccount(account);
        
        if(createdAccount != null)
        {
            ctx.status(200).json(createdAccount);
        }
        else
        {
            //Account does not exist
            ctx.status(400).result("");
        }
    }

    private void loginAccount(Context ctx)
    {
        Account account = ctx.bodyAsClass(Account.class);
        Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
        
        if(loggedInAccount != null)
        {
            ctx.status(200).json(loggedInAccount);
        }
        else
        {
            //Account does not exist
            ctx.status(401).result("");
        }
    }

    //Handlers for messages
    private void createMessage(Context ctx)
    {
        Message message = ctx.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message);

        if(createdMessage != null)
        {
            ctx.status(200).json(createdMessage);
        }
        else
        {
            //Message does not exist
            ctx.status(400).result("");
        }
    }

    private void getAllMessages(Context ctx)
    {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageById(Context ctx)
    {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if(message != null)
        {
            ctx.json(message);
        }
        else
        {
            //Message does not exist
            ctx.status(200).result("");
        }
    }

    private void deleteMessageById(Context ctx)
    {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageToDelete = messageService.getMessageById(messageId);

        if(messageToDelete != null)
        {
            boolean deleted = messageService.deleteMessageById(messageId);
            if(deleted)
            {
                ctx.status(200).json(messageToDelete);
            }
            else
            {
                ctx.status(500).result("");
            }
        }
        else
        {
            //Message does not exist
            ctx.status(200).result("");
        }
        
    }

    private void updateMessageText(Context ctx)
    {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        String newMessageText = ctx.bodyAsClass(Message.class).getMessage_text();
        Message updatedMessage = messageService.updateMessageText(messageId, newMessageText);

        if(updatedMessage != null)
        {
            ctx.status(200).json(updatedMessage);
        }
        else
        {
            //Message does not exist
            ctx.status(400).result("");
        }
    }

    private void getMessagesByUser(Context ctx)
    {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(accountId);
        ctx.json(messages);
    }
}