package com.samuelTI.smartpoint.api.utils;

import com.samuelTI.smartpoint.api.dtos.CadastroLancamentoDto;
import com.samuelTI.smartpoint.api.entities.Funcionario;
import com.samuelTI.smartpoint.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Optional;

@Configuration
public class Validations {

    private static final Logger log = LoggerFactory.getLogger(Validations.class);

    public void validCustomer(CadastroLancamentoDto cadastroLancamentoDto, FuncionarioService service, BindingResult result) {
        if (cadastroLancamentoDto.getFuncionarioId() == null) {
            result.addError(new ObjectError("Employee", "Employee not found"));
            return;
        }

        log.info("Validing employee id {}: ", cadastroLancamentoDto.getFuncionarioId());
        Optional<Funcionario> funcionario = service.buscarPorId(cadastroLancamentoDto.getFuncionarioId());
        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("Employee", "Employee not found. ID nonexistent."));
        }
    }
}
