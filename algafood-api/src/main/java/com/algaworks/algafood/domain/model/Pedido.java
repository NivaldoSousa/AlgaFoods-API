package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	
	@Embedded
	private Endereco enderecoEntrega;

	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;
	
	@CreationTimestamp
	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false )
	private Usuario cliente;

	/*
	* sem o cascade quando o pedido tiver ItemPedido nao será salvo
	* */
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	public void calcularValorTotal() {
		getItens().forEach(ItemPedido::calcularPrecoTotal);

		this.subtotal = getItens().stream()
				.map(item -> item.getPrecoTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		this.valorTotal = this.subtotal.add(this.taxaFrete);
	}

	public void definirFrete() {
		setTaxaFrete(BigDecimal.valueOf(getRestaurante().getTaxaFrete()));
	}

	public void atribuirPedidoAosItens() {
		getItens().forEach(item -> item.setPedido(this));
	}
}
