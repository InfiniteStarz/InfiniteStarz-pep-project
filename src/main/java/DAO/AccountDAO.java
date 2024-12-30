package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO 
{
    // Method to create a new account in the database
    public Account createAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            //SQL code
            String sql = "INSERT INTO account (username, password) VALUES (?,?)"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0)
            {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next())
                {
                    account.setAccount_id(generatedKeys.getInt(1));
                }
                return account;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve an account by its ID
    public Account getAccountById(int id)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            //SQL code
            String sql = "SELECT * FROM account WHERE account_id = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return new Account(
                    resultSet.getInt("account_id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Method to retrieve an account by username and password (for login validation)
    public Account getAccountByUsernameAndPassword(String username, String password)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?"; //SQL code
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,username);
            preparedStatement.setString(2, password);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) 
            {
                return new Account(
                    resultSet.getInt("account_id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
