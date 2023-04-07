package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.v1.model.EnderecoModel;
import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Classe de configuração do modelMapper para returnar um instancia do mesmo para ter acesso ao metodos do de/para
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        /**
         * esse trecho faz com que vc consiga fazer o de/para com um atributo especifico
         * quando os nomes sao diferentes
         * por exemplo o valor da atributo taxaFrete para preço.
         */
        //modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
        //		.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setTaxaFrete);

        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
        enderecoToEnderecoModelTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }
}
