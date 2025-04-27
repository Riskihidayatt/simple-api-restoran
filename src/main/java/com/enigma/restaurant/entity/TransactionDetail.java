package com.enigma.restaurant.entity;

import com.enigma.restaurant.constant.TableName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableName.TRANSACTION_DETAIL)
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, columnDefinition = "NUMERIC(10,2)") // Harga saat transaksi
    private Double price;

    @Column(name = "subtotal", nullable = false, columnDefinition = "NUMERIC(10,2)")
    private Double subtotal;
}
