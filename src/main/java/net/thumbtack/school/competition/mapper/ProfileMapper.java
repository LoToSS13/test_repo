package net.thumbtack.school.competition.mapper;

import net.thumbtack.school.competition.dto.response.IdDtoResponse;

public class ProfileMapper {
    public static IdDtoResponse fromProfileIds(Integer id){
        return new IdDtoResponse(id);
    }


}
