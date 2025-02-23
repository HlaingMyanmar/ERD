package org.erd.categoryoptions.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_id;

    @NotBlank(message = "အမျိုးအစား အမည်နာမထည့်ရန်လိုအပ်သည်။")
    @Column(name = "category_name",length = 150)
    private String category_name ;

    @Column(name = "description")
    private String description ;

    public Category(String category_name, String description) {
        this.category_name = category_name;
        this.description = description;
    }
}
