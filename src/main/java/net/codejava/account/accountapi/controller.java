package net.codejava.account.accountapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by Thilak
 * Created on 16/03/24.
 * Class: controller.java
 */
@RestController
@RequestMapping("/api/accounts")
public class controller {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountModelAssembler accountModelAssembler;

    @GetMapping
    public ResponseEntity <CollectionModel<EntityModel<Account>>> listAll(){
        List<Account> all = accountService.findAll();
        if (all.isEmpty())
            return ResponseEntity.noContent().build();
        CollectionModel<EntityModel<Account>> collectionModel = accountModelAssembler.toCollectionModel(all);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Account>> getAccountUsingId(@PathVariable String id){
        try {
            Account account = accountService.get(id);
            EntityModel<Account> model = accountModelAssembler.toModel(account);
            return new ResponseEntity<>(model,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Account>> add(@RequestBody Account account){
        Account save = accountService.save(account);
        EntityModel<Account> model = accountModelAssembler.toModel(account);
        return ResponseEntity.created(linkTo(methodOn(controller.class).getAccountUsingId(save.getId())).toUri()).body(model);
    }

    @PutMapping
    public ResponseEntity<EntityModel<Account>> update(@RequestBody Account account){
        Account save = accountService.save(account);
        EntityModel<Account> model = accountModelAssembler.toModel(save);
        return new ResponseEntity<>(model,HttpStatus.OK);

    }
    @PatchMapping("/{id}/deposit")
    public ResponseEntity<EntityModel<Account>> deposit(@PathVariable String id,@RequestBody Amount amount){
        Account deposite = accountService.deposite(amount.getAmount(), id);
        EntityModel<Account> model = accountModelAssembler.toModel(deposite);
        return new ResponseEntity<>(model,HttpStatus.OK);
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<EntityModel<Account>> withdraw(@PathVariable String id,@RequestBody Amount amount){
        Account withDraw = accountService.withDraw(amount.getAmount(), id);
        EntityModel<Account> model = accountModelAssembler.toModel(withDraw);
        return new ResponseEntity<>(model,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/saveAll")
    public ResponseEntity<CollectionModel<EntityModel<Account>>> addAccounts(@RequestBody List<Account> account){
        List<Account> accounts = accountService.saveAll(account);
        CollectionModel<EntityModel<Account>> collectionModel = accountModelAssembler.toCollectionModel(accounts);
        return new ResponseEntity<>(collectionModel,HttpStatus.OK);
    }


}
