package com.bankofaffiliates.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;

    @NonNull
    private Role role;

    @NonNull
    private Status status;

    @NonNull
    private String emailRecoveryToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emailRecoveryTokenDateExpired;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    public enum Status {
        NEW, CONFIRMED, BLOCKED
    }

    public enum Role {
        ROLE_CLIENT, ROLE_ADMIN
    }

}
