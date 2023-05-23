package net.thumbtack.school.competition.mapper;

import net.thumbtack.school.competition.dto.request.FundDtoRequest;

import net.thumbtack.school.competition.models.Fund;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FundMapper {
    FundMapper MAPPER = Mappers.getMapper(FundMapper.class);

    Fund toFund(FundDtoRequest fundDto);
}
