package org.example;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articulo")
@NoArgsConstructor      //Constructor Vacio
@AllArgsConstructor     //Constructor con todos los atributos
@RequiredArgsConstructor
@Data                   //Con Data obtengo todos los getters and setters
@Audited
@Builder
public class Articulo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    @NonNull private int cantidad;

    @Column(name = "denominacion")
    @NonNull private String denominacion;

    @Column(name = "precio")
    @NonNull private int precio;

    @Builder.Default
    @OneToMany(mappedBy = "articulo", cascade = CascadeType.PERSIST)
    private List<DetalleFactura> detalle = new ArrayList<DetalleFactura>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "articulo_categoria",
            joinColumns = @JoinColumn(name = "articulo_id"),
            inverseJoinColumns =
            @JoinColumn(name = "categoria_id")
    )

    @Builder.Default
    private List<Categoria> categorias = new ArrayList<Categoria>();


}
