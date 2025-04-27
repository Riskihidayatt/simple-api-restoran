package com.enigma.restaurant.entity;


import com.enigma.restaurant.constant.TableName;
import com.enigma.restaurant.enums.MenuCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableName.MENU)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false )
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, columnDefinition = "NUMERIC(10,2)")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    private MenuCategory category;

    @Column(name = "is_available", columnDefinition = "BOOLEAN DEFAULT TRUE")
    @Builder.Default
    private Boolean isAvailable = true;
}
