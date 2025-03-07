package com.samuelTI.smartpoint.api.mapper;

import com.samuelTI.smartpoint.api.dtos.CadastroLancamentoDto;
import com.samuelTI.smartpoint.api.entities.Funcionario;
import com.samuelTI.smartpoint.api.entities.Lancamento;
import com.samuelTI.smartpoint.api.enums.TipoEnum;
import com.samuelTI.smartpoint.api.services.LancamentoService;
import com.sun.xml.messaging.saaj.packaging.mime.internet.ParseException;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Optional;

@Component
public class LancamentoMapper {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CadastroLancamentoDto convertLancamentoDto(Lancamento lancamento) {

        CadastroLancamentoDto cadastroLancamentoDto = new CadastroLancamentoDto();
        cadastroLancamentoDto.setId(Optional.of(lancamento.getId()));
        cadastroLancamentoDto.setClockTime(this.dateFormat.format(Date.from(Instant.now())));
        cadastroLancamentoDto.setTipo(lancamento.getTipo().toString());
        cadastroLancamentoDto.setDescricao(lancamento.getDescricao());
        cadastroLancamentoDto.setLocalizacao(lancamento.getLocalizacao());
        cadastroLancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());


        return cadastroLancamentoDto;
    }

    public CadastroLancamentoDto convertLancamentoFindId(Lancamento lancamento) {

        CadastroLancamentoDto cadastroLancamentoDto = new CadastroLancamentoDto();
        cadastroLancamentoDto.setId(Optional.of(lancamento.getId()));
        cadastroLancamentoDto.setClockTime(lancamento.getClockTime().toString());
        cadastroLancamentoDto.setTipo(lancamento.getTipo().toString());
        cadastroLancamentoDto.setDescricao(lancamento.getDescricao());
        cadastroLancamentoDto.setLocalizacao(lancamento.getLocalizacao());
        cadastroLancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());


        return cadastroLancamentoDto;
    }

    public Lancamento convertDtoByLancamento(CadastroLancamentoDto cadastroLancamentoDto,
                                             LancamentoService lancamentoService,
                                             BindingResult result) throws ParseException, java.text.ParseException{
        var lancamento = new Lancamento();

        if(cadastroLancamentoDto.getId().isPresent()) {
            Optional<Lancamento> lanc = lancamentoService.buscarById(cadastroLancamentoDto.getId().get());
            if(lanc.isPresent()) {
                lancamento = lanc.get();
            }else {
                result.addError(new ObjectError("lancamento", "Launch not found."));
            }

        }else {
            lancamento.setFuncionario(new Funcionario());
            lancamento.getFuncionario().setId(cadastroLancamentoDto.getFuncionarioId());
        }

        lancamento.setDescricao(cadastroLancamentoDto.getDescricao());
        lancamento.setLocalizacao(cadastroLancamentoDto.getLocalizacao());
        lancamento.setClockTime(this.dateFormat.parse(cadastroLancamentoDto.getClockTime()));

        if(EnumUtils.isValidEnum(TipoEnum.class, cadastroLancamentoDto.getTipo())) {
            lancamento.setTipo(TipoEnum.valueOf(cadastroLancamentoDto.getTipo()));

        }else {
            result.addError(new ObjectError("Tipo" , "Invalid type"));
        }
        return lancamento;
    }
}
