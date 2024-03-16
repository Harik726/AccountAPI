package net.codejava.account.accountapi;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created by Thilak
 * Created on 16/03/24.
 * Class: Account.java
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 20, nullable = false,unique = true)
    private String accountNumber;
    private float balance;
}
