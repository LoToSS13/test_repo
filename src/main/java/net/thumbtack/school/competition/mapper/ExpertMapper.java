package net.thumbtack.school.competition.mapper;


import net.thumbtack.school.competition.dto.request.ExpertDtoRequest;
import net.thumbtack.school.competition.models.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpertMapper {
    ExpertMapper MAPPER = Mappers.getMapper(ExpertMapper.class);

    Expert toExpert(ExpertDtoRequest expertDTO);
}
