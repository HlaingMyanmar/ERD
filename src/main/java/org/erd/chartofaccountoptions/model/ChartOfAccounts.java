package org.erd.chartofaccountoptions.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "chart_of_accounts")
@Component
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

   public ChartOfAccounts(String account_name, String account_type) {
      this.account_name = account_name;
      this.account_type = account_type;
   }

   public ChartOfAccounts(String account_name, String account_type, byte is_active) {
      this.account_name = account_name;
      this.account_type = account_type;
      this.is_active = is_active;
   }
}
