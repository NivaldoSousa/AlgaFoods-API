package com.algaworks.algafood.api.v1.model.input;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class FotoProdutoInput {

    @ApiModelProperty(hidden = true)
    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
    private MultipartFile arquivo;

    @ApiModelProperty(value = "Descrição da foto do produto", required = true)
    @NotBlank
    private String descricao;
}
