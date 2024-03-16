package net.codejava.account.accountapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Configuration
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {

    @Override
    public EntityModel<Account> toModel(Account account) {
         EntityModel<Account> entityModel=EntityModel.of(account);

        entityModel.add(linkTo(methodOn(controller.class).getAccountUsingId(account.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(controller.class).deposit(account.getId(),null)).withRel("deposit"));
        entityModel.add(linkTo(methodOn(controller.class).withdraw(account.getId(), null)).withRel("withdraw"));
        entityModel.add(linkTo(methodOn(controller.class).listAll()).withRel(IanaLinkRelations.COLLECTION));


        return entityModel;

    }
}
