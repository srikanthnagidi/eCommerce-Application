package com.example.udacitycource.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    @Column
    private Long id;

    @OneToOne(mappedBy = "cart")
    @JsonProperty
    private User user;

    @ManyToMany
    @JsonProperty
    @Column
    private List<Item> itemList;

    @Column
    @JsonProperty
    private BigDecimal total;

    public void addItem(Item item){
        if (itemList == null) itemList = new ArrayList<>();
        itemList.add(item);

        if (total == null) total =new BigDecimal(0);
        total = total.add(item.getPrice());
    }

    public void removeItem(Item item){
        if (itemList == null) {
            itemList = new ArrayList<>();
            return;
        }
        itemList.remove(item);
        if (total == null){
            total = new BigDecimal(0);
            return;
        }
        total = total.subtract(item.getPrice());
        total = (total.compareTo(new BigDecimal(0))<=0)?new BigDecimal(0):total;
    }
}
