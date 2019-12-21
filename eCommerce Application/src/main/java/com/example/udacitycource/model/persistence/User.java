package com.example.udacitycource.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @JsonProperty
    @Setter private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonIgnore
    @Setter
    private Cart cart;

    public void setPassword(String password, boolean salt){
        if (salt){
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        }else {
            this.password = password;
        }
    }
}
