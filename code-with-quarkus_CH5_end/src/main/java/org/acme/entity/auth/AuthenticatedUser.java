package org.acme.entity.auth;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@UserDefinition
@Entity
@Table(name = "users")
public class AuthenticatedUser extends PanacheEntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "users_user_id_seq")
    @Column(name = "user_id")
    public Long id;

    @Username
    @Column(name = "user_name", nullable = false, unique = true)
    public String username;

    @Password
    @Column(name = "password_hash", nullable = false, length = 500)
    public String password;

    @Roles
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "user_role_mapping",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    public List<Role> roles = new ArrayList<>();

    public static void add(String username, String password, Role role) {
            AuthenticatedUser user = new AuthenticatedUser();
            user.username = username;
            user.password = BcryptUtil.bcryptHash(password);
            user.roles.add(role);
            user.persist();
    }

}
