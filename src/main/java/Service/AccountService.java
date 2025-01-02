package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account){
        Account newAccount = accountDAO.registerAccount(account);
        return newAccount;
    }


    public Account accessAccount(String username, String password) {
        Account loginAccount = accountDAO.accessAccount(username, password);
        return loginAccount;
    }
    

    
}
