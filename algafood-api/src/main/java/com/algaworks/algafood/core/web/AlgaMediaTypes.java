package com.algaworks.algafood.core.web;

import org.springframework.http.MediaType;

/*
 * Classe de constantes de versionamento por MediaType
 * */
public class AlgaMediaTypes {

    public static final String V1_APPLICATION_JASON_VALUE = "application/vnd.algafood.v1+json";

    public static final MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JASON_VALUE);

    public static final String V2_APPLICATION_JASON_VALUE = "application/vnd.algafood.v2+json";

    public static final MediaType V2_APPLICATION_JSON = MediaType.valueOf(V2_APPLICATION_JASON_VALUE);
}
