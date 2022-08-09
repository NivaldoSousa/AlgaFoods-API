package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {


	@EqualsAndHashCode.Include
	@Id
	@Column(name = "produto_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY) // evita de fica sempre trazendo o produto junto, só quando necessario .get()
	@MapsId // mapeia o id da tabela produto com o produto_id, assim atraves da fotoProduto consegue chegar no produto
	private Produto produto;

	private String nomeArquivo;

	private String descricao;

	private String contentType;

	private Long tamanho;
}