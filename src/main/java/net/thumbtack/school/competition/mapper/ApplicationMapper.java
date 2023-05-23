package net.thumbtack.school.competition.mapper;


import net.thumbtack.school.competition.dto.request.ApplicationDtoRequest;
import net.thumbtack.school.competition.models.Application;
import net.thumbtack.school.competition.models.Participant;

import java.util.ArrayList;


public class ApplicationMapper {

    public static Application toApplication(ApplicationDtoRequest applicationDto){
        return new Application(
                (Participant) null, applicationDto.getName(), applicationDto.getDescription(), (ArrayList<Integer>) applicationDto.getProfiles(), applicationDto.getAmount());
    }

    public static ApplicationDtoRequest fromApplication(Application application){
        return new ApplicationDtoRequest(
                application.getName(),
                application.getDescription(),
                application.getProfiles(),
                application.getAmount()
        );
    }
}
