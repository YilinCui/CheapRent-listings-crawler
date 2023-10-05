package com.example.backend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;  // MEDIUMINT 对应 Java 的 Integer 类型

    @Column(name = "link")
    private String link;  // VARCHAR 对应 Java 的 String 类型

    @Column(name = "suite")
    private String suite;  // VARCHAR 对应 Java 的 String 类型

    @Column(name = "price")
    private Integer price;  // INT 对应 Java 的 Integer 类型

    @Column(name = "location")
    private String location;  // VARCHAR 对应 Java 的 String 类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
