package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    /*
    * Retorna uma entidade de modelo apartir de um dto
    * */
    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    /*
     * Receber o dado de entrada dto e uma instancia da entidade de modelo para ser populado com os o mesmo dados
     * */
    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
