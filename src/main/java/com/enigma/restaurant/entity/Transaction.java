package com.enigma.restaurant.entity;
import com.enigma.restaurant.constant.TableName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableName.TRANSACTION)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TransactionDetail> transactionDetails = new HashSet<>();

    @Column(name = "total_amount", nullable = false, columnDefinition = "NUMERIC(12,2)")
    private Double totalAmount;

    @Column(name = "transaction_date", nullable = false)
    @Builder.Default
    private LocalDateTime transactionDate = LocalDateTime.now();

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public void addTransactionDetail(TransactionDetail detail) {
        transactionDetails.add(detail);
        detail.setTransaction(this);
    }
}