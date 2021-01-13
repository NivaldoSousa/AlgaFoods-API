package com.algaworks.algafood.domain.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
@JsonRootName("gastronomia")
@Data // essa antaçao do lombok possui anotaçoes já inclusas como: @Getter, @Setter, @ToString, @EqualsAndHashCode
@EqualsAndHashCode(onlyExplicitlyIncluded = true) /*essa propriedade do EqualsAndHashCode diz que somente atributo
que for escolher explicitamente sera incluso */
@Entity
public class Cozinha {

    @EqualsAndHashCode.Include // deixando o atributo explicitamente incluso.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("titulo")
    private String nome;

}
