package org.erd.categoryoptions.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_id;

    @NotBlank(message = "အမျိုးအစား အမည်နာမထည့်ရန်လိုအပ်သည်။")
    @Size(max = 150)
    @Column(name = "category_name", nullable = false,length = 150)
    private String category_name ;

    @Column(name = "description")
    private String description ;
}
