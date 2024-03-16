package net.codejava.account.accountapi;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepo repo;

    public Account save(Account account){
        return repo.save(account);
    }
    public Account get(String id){
        return repo.findById(id).get();
    }

    public List<Account> findAll(){
        return repo.findAll();
    }

    public Account deposite(float amount ,String id){
        repo.updateBalance(amount,id);
        return  repo.findById(id).get();
    }

    public Account withDraw(float amount ,String id){
        repo.updateBalance(-amount,id);
        return  repo.findById(id).get();
    }

    public void deleteAccount(String id) throws AccountNotFoundException {
        if (!repo.existsById(id))
            throw new AccountNotFoundException("not found");
        else
            repo.deleteById(id);
    }

    public List<Account> saveAll(List<Account> accounts){
       return repo.saveAll(accounts);
    }

}
