package de.maxkorte.inventorytracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String serialNumber;
    private String customer;
    private String condition;
    private String location;
    private Double priceIn;
    private Double priceOut;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> imageFileNames;
}
