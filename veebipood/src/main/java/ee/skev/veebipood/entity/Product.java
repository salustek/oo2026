package ee.skev.veebipood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean active;
    private int stock;

    // @ManyToMany --> private List<Ingredients> ingredients
    // @OneToMany --> private List<Ingredients> ingredients
    // @ManyToOne --> tooted jagavad seda kategooriat
    // @OneToOne --> tooted ei jaga seda kategooriat

    @ManyToOne
    private Category category; // automaatselt võõrvõtmega (@Id väljaga) siia tabelisse

}