package org.erd.chartofaccountoptions.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.erd.transactionoptions.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "chart_of_accounts")
@Component
@ToString
public class ChartOfAccounts {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int account_id ;

   @NotBlank(message = "အကောင့်နာမည်လိုအပ်ပါသည်။")
   @Column(name = "account_name")
   private String  account_name;

   @NotBlank(message = "အကောင့် အမျိုးအစား အထူးလိုအပ်ပါသည်")
   @Column(name = "account_type")
   private String  account_type ;

   @Column(name = "is_active")
   private byte is_active ;

   private String activation;

   @OneToMany(mappedBy = "account_id", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
   private Set<Transaction> transactionsSet;



   public ChartOfAccounts(String account_name, String account_type) {
      this.account_name = account_name;
      this.account_type = account_type;
   }

   public ChartOfAccounts(String account_name, String account_type, byte is_active) {
      this.account_name = account_name;
      this.account_type = account_type;
      this.is_active = is_active;
   }

   public ChartOfAccounts(int account_id, String account_name, String account_type, byte is_active) {
      this.account_id = account_id;
      this.account_name = account_name;
      this.account_type = account_type;
      this.is_active = is_active;
   }

   public ChartOfAccounts(int account_id, String account_name, String account_type, String activation) {
      this.account_id = account_id;
      this.account_name = account_name;
      this.account_type = account_type;
      this.activation = activation;
   }
}
