package com.example.udacitycource.model.persistence;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "user_order")
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @Column
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonProperty
    @Column
    private List<Item> itemList;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @JsonProperty
    private User user;

    @JsonProperty
    @Column
    private BigDecimal total;

    public static UserOrder createFromCart(Cart cart){
        UserOrder userOrder = new UserOrder();
        userOrder.setItemList(cart.getItemList());
        userOrder.setTotal(cart.getTotal());
        userOrder.setUser(cart.getUser());
        return userOrder;
    }

}
