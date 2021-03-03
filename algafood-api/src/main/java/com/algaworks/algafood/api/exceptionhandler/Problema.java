package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // essa anotaçao e um construtor, pois elimina o codigo new algumaclasse.
public class Problema {

	private LocalDateTime dataHora;
	private String mensagem;
}
