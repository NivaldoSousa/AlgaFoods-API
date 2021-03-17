package com.algaworks.algafood.domain.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data // essa antaçao do lombok possui anotaçoes já inclusas como: @Getter, @Setter, @ToString, @EqualsAndHashCode
@EqualsAndHashCode(onlyExplicitlyIncluded = true) /*essa propriedade do EqualsAndHashCode diz que somente atributo
que for escolher explicitamente sera incluso */
@Entity
public class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class)
	@EqualsAndHashCode.Include // deixando o atributo explicitamente incluso.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>(); // o new ArrayList é por boas praticas pra evitar exceptions
}
