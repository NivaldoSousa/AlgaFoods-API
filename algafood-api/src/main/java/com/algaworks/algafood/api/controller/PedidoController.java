package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.model.input.PedidoModel;
import com.algaworks.algafood.api.model.input.PedidoResumoModel;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;


    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    /*
     * Pageable - paginação, seria o numero da pagina
     * param -> PedidoFilter filtro de pesquisa customizada
     * param -> @PageableDefault(size = 10) -> anotação que serve para dizer quantos elementos por default será retorna pela paginação
     * param -> Pageable -> Interface abstrata para informações de paginação
     * */
    @GetMapping
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpec.usandoFiltro(filtro), pageable);

        List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler.toCollectionModel(pedidosPage.getContent()); // getContent extrai a lista de cozinha daquela pagina e devolve um List

        Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(pedidosResumoModel, pageable, pedidosPage.getTotalElements()); // tranformando a list de PedidoResumoModel em um page

        return pedidosResumoModelPage;
    }

       /* FORMA DE FAZER UM FILTER DE CAMPOS COM @JsonFilter
       * @JsonFilter -> filtro logico, para filtrar propriedades da classe , NESTE CASO A ANOTAÇÃO @JsonFilter("pedidoFilter") FOI USADO NA CLASSE PedidoResumoModel
       * @GetMapping("/filter")
      public MappingJacksonValue listarPorFiltro(@RequestParam(required = false) String campos) {
          List<Pedido> pedidos = pedidoRepository.findAll();
          List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);

          MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel); // envelopar

          SimpleFilterProvider filterProvider = new SimpleFilterProvider();  // instancia a classe para a filtragem

          if(StringUtils.isNotBlank(campos)){
              // filterOutAllExcept recebe um array, com isso recbemos por parametro os campos informados pelo consumido da API, como o valor da string seráseparada do virgular os campos basta usar o split que ele retorna um array
              filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
          }else{
              filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll()); // passa o nome do filter do @JsonFilter, SimpleBeanPropertyFilter diz o tipo de filtro que irá funcionar nesse caso ele ira mostrar todos os atrinutos
          }

          pedidosWrapper.setFilters(filterProvider);

          return pedidosWrapper;
      }*/
}
