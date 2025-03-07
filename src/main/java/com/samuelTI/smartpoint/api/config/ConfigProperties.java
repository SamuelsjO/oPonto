package com.samuelTI.smartpoint.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ConfigProperties {

    private final int qtdPorPagina;

    public ConfigProperties(@Value("${paginacao.qtd_por_pagina}") int qtdPorPagina) {
        this.qtdPorPagina = qtdPorPagina;
    }
}
