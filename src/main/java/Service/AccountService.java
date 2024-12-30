package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService 
{
    private AccountDAO accountDAO = new AccountDAO();

    //Method to validate account details
    private boolean isValidAccount(Account account)
    {
        return account.getUsername() != null && !account.getUsername().isBlank()
            && account.getPassword() != null && account.getPassword().length() >= 4;
    }

    //Method to handle account creation
    public Account registerAccount(Account account)
    {
        if(isValidAccount(account))
        {
            Account existingAccount = accountDAO.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
            if(existingAccount == null)
            {
                return accountDAO.createAccount(account);
            }
        }
        return null; //Return null for invalid or duplicate accounts
    }

    //Method to handle login
    public Account login(String username, String password)
    {
        if (username != null && !username.isBlank() && password != null && password.length() >= 4)
        {
            return accountDAO.getAccountByUsernameAndPassword(username, password);
        }

        return null; //Return null for invalid username or password
    }

    //Method to retrieve account by ID
    public Account getAccountById(int id)
    {
        return accountDAO.getAccountById(id);
    }
}
