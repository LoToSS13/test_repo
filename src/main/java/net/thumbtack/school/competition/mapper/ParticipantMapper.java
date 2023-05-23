package net.thumbtack.school.competition.mapper;

import net.thumbtack.school.competition.dto.request.ParticipantDtoRequest;
import net.thumbtack.school.competition.models.Participant;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParticipantMapper {
    ParticipantMapper MAPPER = Mappers.getMapper(ParticipantMapper.class);

    Participant toParticipant(ParticipantDtoRequest registerParticipantDTO);

    @InheritInverseConfiguration
    ParticipantDtoRequest fromParticipant(Participant participant);
}
