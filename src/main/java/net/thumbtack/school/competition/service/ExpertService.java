package net.thumbtack.school.competition.service;

import com.google.gson.Gson;
import net.thumbtack.school.competition.dao.ApplicationDao;
import net.thumbtack.school.competition.dao.UserDao;
import net.thumbtack.school.competition.daoimpl.ApplicationDaoImpl;
import net.thumbtack.school.competition.daoimpl.UserDaoImpl;
import net.thumbtack.school.competition.dto.request.*;
import net.thumbtack.school.competition.dto.response.ApplicationDtoResponse;
import net.thumbtack.school.competition.dto.response.EmptyDtoResponse;
import net.thumbtack.school.competition.dto.response.SortedApplicationDtoResponse;
import net.thumbtack.school.competition.exception.ErrorCode;
import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.mapper.ApplicationMapper;
import net.thumbtack.school.competition.mapper.ExpertMapper;
import net.thumbtack.school.competition.models.Application;
import net.thumbtack.school.competition.models.Expert;
import net.thumbtack.school.competition.models.User;
import net.thumbtack.school.competition.server.ServerResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpertService extends ValidatingUtils {

    private final static Gson gson = new Gson();

    private final UserDao userDao = new UserDaoImpl();

    private final ApplicationDao applicationDao = new ApplicationDaoImpl();



    public ServerResponse register(String requestJsonString) {
        try{

            ExpertDtoRequest registerExpertDto = GenericUtils.getClassFromJson(requestJsonString, ExpertDtoRequest.class);
            validateExpertRegister(registerExpertDto);

            Expert expert = ExpertMapper.MAPPER.toExpert(registerExpertDto);

            userDao.insertToDatabase(expert);

            return new ServerResponse( gson.toJson(new EmptyDtoResponse()));
        } catch (ServerException e){

            return new ServerResponse(e);
        }
    }

    public ServerResponse getApplicationsByProfiles(String token, String json){
        try{

            validateData(token,json);

            SortedApplicationDtoRequest wishProfiles = GenericUtils.getClassFromJson(json, SortedApplicationDtoRequest.class);
            if (wishProfiles.getProfiles()==null){
                throw new ServerException(ErrorCode.WRONG_APPLICATION_PROFILES);
            }

            List<Integer> wantedProfiles = wishProfiles.getProfiles();
            User foundedUser = userDao.getActiveUser(token);

            validateFoundedUser(foundedUser, Expert.class);

            Expert foundedExpert = (Expert)foundedUser;

            List<Integer> profilesListExpert = foundedExpert.getProfiles();
            for (Integer wishesCourse: wantedProfiles){
                if (!profilesListExpert.contains(wishesCourse)){
                    throw new ServerException(ErrorCode.WRONG_PROFILES);
                }
            }

            Set<Integer> foundedApplications = applicationDao.applicationsByProfiles(wantedProfiles);

            SortedApplicationDtoResponse response = new SortedApplicationDtoResponse(foundedApplications);

            return new ServerResponse( gson.toJson(response));

        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse markApplication(String token, String json){
        try{
            validateData(token,json);

            MarkApplicationDtoRequest markApplication = GenericUtils.getClassFromJson(json, MarkApplicationDtoRequest.class);
            ParticipantService.validateApplication(markApplication.getMarkedApplication());
            if (markApplication.getMark()<=0||markApplication.getMark()>5){
                throw new ServerException(ErrorCode.WRONG_APPLICATION_MARK);
            }

            User foundedUser = userDao.getActiveUser(token);
            validateFoundedUser(foundedUser, Expert.class);
            Application application = ApplicationMapper.toApplication(markApplication.getMarkedApplication());

            applicationDao.setMarkToApplication((Expert)foundedUser, markApplication.getMark(), application);

            ApplicationDtoResponse response = new ApplicationDtoResponse("Заявка успешно оценена");

            return new ServerResponse(gson.toJson(response));
        } catch (ServerException e){

            return new ServerResponse(e);
        }
    }


    public static void validateExpertRegister(ExpertDtoRequest registerExpertDto) throws ServerException {
        if(!validateInput(registerExpertDto.getName())){
            throw new ServerException(ErrorCode.WRONG_NAME);
        }

        if (!validateInput(registerExpertDto.getLogin())){
            throw new ServerException(ErrorCode.WRONG_LOGIN);
        }

        if (!validatePassword(registerExpertDto.getPassword())){
            throw new ServerException(ErrorCode.WRONG_PASSWORD);
        }
        if (registerExpertDto.getProfiles() == null  || registerExpertDto.getProfiles().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_PROFILES);
        }
    }

    public static void validateData(String token, String json) throws ServerException {
        if (!validateInput(json)){

            throw new ServerException(ErrorCode.WRONG_JSON);
        }
        if (!validateInput(token)){
            throw new ServerException(ErrorCode.WRONG_REQUEST);
        }
    }

}
