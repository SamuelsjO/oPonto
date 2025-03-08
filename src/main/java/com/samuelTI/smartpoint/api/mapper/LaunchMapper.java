package com.samuelTI.smartpoint.api.mapper;

import com.samuelTI.smartpoint.api.dtos.LaunchRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.entities.Launch;
import com.samuelTI.smartpoint.api.enums.TipoEnum;
import com.samuelTI.smartpoint.api.services.LaunchService;
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
public class LaunchMapper {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public LaunchRegistrationDto convertLancamentoDto(Launch launch) {

        LaunchRegistrationDto launchRegistrationDto = new LaunchRegistrationDto();
        launchRegistrationDto.setId(Optional.of(launch.getId()));
        launchRegistrationDto.setClockTime(this.dateFormat.format(Date.from(Instant.now())));
        launchRegistrationDto.setTipo(launch.getTipo().toString());
        launchRegistrationDto.setDescricao(launch.getDescricao());
        launchRegistrationDto.setLocalizacao(launch.getLocalizacao());
        launchRegistrationDto.setFuncionarioId(launch.getCustomer().getId());


        return launchRegistrationDto;
    }

    public LaunchRegistrationDto convertLancamentoFindId(Launch launch) {

        LaunchRegistrationDto launchRegistrationDto = new LaunchRegistrationDto();
        launchRegistrationDto.setId(Optional.of(launch.getId()));
        launchRegistrationDto.setClockTime(launch.getClockTime().toString());
        launchRegistrationDto.setTipo(launch.getTipo().toString());
        launchRegistrationDto.setDescricao(launch.getDescricao());
        launchRegistrationDto.setLocalizacao(launch.getLocalizacao());
        launchRegistrationDto.setFuncionarioId(launch.getCustomer().getId());


        return launchRegistrationDto;
    }

    public Launch convertDtoByLancamento(LaunchRegistrationDto launchRegistrationDto,
                                         LaunchService launchService,
                                         BindingResult result) throws ParseException, java.text.ParseException{
        var lancamento = new Launch();

        if(launchRegistrationDto.getId().isPresent()) {
            Optional<Launch> lanc = launchService.buscarById(launchRegistrationDto.getId().get());
            if(lanc.isPresent()) {
                lancamento = lanc.get();
            }else {
                result.addError(new ObjectError("lancamento", "Launch not found."));
            }

        }else {
            lancamento.setCustomer(new Customer());
            lancamento.getCustomer().setId(launchRegistrationDto.getFuncionarioId());
        }

        lancamento.setDescricao(launchRegistrationDto.getDescricao());
        lancamento.setLocalizacao(launchRegistrationDto.getLocalizacao());
        lancamento.setClockTime(this.dateFormat.parse(launchRegistrationDto.getClockTime()));

        if(EnumUtils.isValidEnum(TipoEnum.class, launchRegistrationDto.getTipo())) {
            lancamento.setTipo(TipoEnum.valueOf(launchRegistrationDto.getTipo()));

        }else {
            result.addError(new ObjectError("Tipo" , "Invalid type"));
        }
        return lancamento;
    }
}
