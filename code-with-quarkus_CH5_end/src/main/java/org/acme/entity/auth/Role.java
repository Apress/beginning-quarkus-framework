package org.acme.entity.auth;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.RolesValue;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "roles_role_id_seq")
    @Column(name = "role_id",nullable = false, unique = false)
    public Long id;

    @Column(name = "role_name",nullable = false)
    @RolesValue
    public String name;

    @ManyToMany(mappedBy = "roles")
    List<AuthenticatedUser> users;
}
