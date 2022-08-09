package com.algaworks.algafood.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize maxSize; //representa um tamanho de dado

    @Override
    public void initialize(FileSize constraintAnnotation) {
        //pega o valor da anotação e faz o parse para DataSize
        this.maxSize = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
         //verifica se esta nulo e se o arquivo obtido é menor ou igual max da anotação
        return  value == null || value.getSize() <= this.maxSize.toBytes();
    }
}
