package com.algaworks.algafood.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

/*
* Classe responsavel por serializador qualquer objeto que seja do tipo Page
* @JsonComponent -> diz que um component spring só que fornece implementações de um serializador ou deserializador que deve ser registrado no jackson
* */
@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        gen.writeStartObject(); // inicio da escrita do objeto em Json
        gen.writeObjectField("content", page.getContent()); // informo o nome do atributo que será representado no Json e adiciono o conteudo vindo do page(objeto retornado pela busca no banco)
        gen.writeNumberField("size", page.getSize()); // informo o nome do atributo do tipo numerico que será representado no Json e adiciono o total de elementos do objeto
        gen.writeNumberField("totalElements", page.getTotalElements());
        gen.writeNumberField("totalPages", page.getTotalPages());
        gen.writeNumberField("number", page.getNumber());
        gen.writeEndObject(); // fim da escrita do objeto em Json
    }
}
