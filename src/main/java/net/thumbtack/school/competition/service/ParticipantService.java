package net.thumbtack.school.competition.service;

import com.google.gson.Gson;
import net.thumbtack.school.competition.dao.ApplicationDao;
import net.thumbtack.school.competition.dao.UserDao;
import net.thumbtack.school.competition.daoimpl.ApplicationDaoImpl;
import net.thumbtack.school.competition.daoimpl.UserDaoImpl;
import net.thumbtack.school.competition.dto.request.ApplicationDtoRequest;
import net.thumbtack.school.competition.dto.request.ParticipantDtoRequest;
import net.thumbtack.school.competition.dto.response.ApplicationDtoResponse;
import net.thumbtack.school.competition.dto.response.EmptyDtoResponse;
import net.thumbtack.school.competition.dto.response.IdDtoResponse;
import net.thumbtack.school.competition.exception.ErrorCode;
import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.mapper.ApplicationMapper;
import net.thumbtack.school.competition.mapper.ParticipantMapper;
import net.thumbtack.school.competition.models.Application;
import net.thumbtack.school.competition.models.Participant;
import net.thumbtack.school.competition.models.User;
import net.thumbtack.school.competition.server.ServerResponse;

public class ParticipantService extends ValidatingUtils {
    private final Gson gson = new Gson();
    private final UserDao userDao = new UserDaoImpl();

    private final ApplicationDao applicationDao = new ApplicationDaoImpl();

    public ServerResponse register(String requestJsonString){
        try {

            ParticipantDtoRequest registerParticipantDto = GenericUtils.getClassFromJson(requestJsonString, ParticipantDtoRequest.class);
            validateRegistration(registerParticipantDto);

            Participant participant = ParticipantMapper.MAPPER.toParticipant(registerParticipantDto);

            userDao.insertToDatabase(participant);

            return new ServerResponse( gson.toJson(new EmptyDtoResponse()));
        } catch (ServerException e){
            return new ServerResponse(e);
        }
    }


    public ServerResponse makeApplication(String token, String requestJsonString) {
        try{
            ApplicationDtoRequest addApplicationDto = GenericUtils.getClassFromJson(requestJsonString, ApplicationDtoRequest.class);
            validateApplication(addApplicationDto);

            Application application = ApplicationMapper.toApplication(addApplicationDto);

            User foundedUser = userDao.getActiveUser(token);
            validateFoundedUser(foundedUser,Participant.class);

            application.setParticipant((Participant)foundedUser);
            Integer id = applicationDao.addApplication(application);

            IdDtoResponse response = new IdDtoResponse(id);
            return new ServerResponse( gson.toJson(response));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse deleteApplication(String token, String requestJsonString) {
        try{
            ApplicationDtoRequest removeApplicationDto = GenericUtils.getClassFromJson(requestJsonString, ApplicationDtoRequest.class);
            validateApplication(removeApplicationDto);

            Application application =  ApplicationMapper.toApplication(removeApplicationDto);

            User foundedUser = userDao.getActiveUser(token);
            validateFoundedUser(foundedUser, Participant.class);

            application.setParticipant((Participant)foundedUser);
            applicationDao.removeApplication(application);

            ApplicationDtoResponse response = new ApplicationDtoResponse("Заявка удалена");
            return new ServerResponse(gson.toJson(response));
        } catch (ServerException e) {
            System.out.println(e.getErrorCode());
            return new ServerResponse(e);
        }
    }

    public static void validateRegistration(ParticipantDtoRequest RegisterParticipant) throws ServerException {
        if(!validateInput(RegisterParticipant.getHeadName())){
            throw new ServerException(ErrorCode.WRONG_NAME);
        }
        if (!validateInput(RegisterParticipant.getLogin())){
            throw new ServerException(ErrorCode.WRONG_LOGIN);
        }
        if (!validatePassword(RegisterParticipant.getPassword())){
            throw new ServerException(ErrorCode.WRONG_PASSWORD);
        }
        if (!validateInput(RegisterParticipant.getCompanyName())){
            throw new ServerException(ErrorCode.WRONG_COMPANY_NAME);
        }
    }

    public static void validateApplication(ApplicationDtoRequest applicationDtoRequest) throws ServerException {
        if (applicationDtoRequest.getAmount()<=0){
            throw new ServerException(ErrorCode.WRONG_APPLICATION_SUM);
        }
        if (!validateInput(applicationDtoRequest.getName())){
            throw new ServerException(ErrorCode.WRONG_APPLICATION_NAME);
        }
        if (!validateInput(applicationDtoRequest.getDescription())){
            throw new ServerException(ErrorCode.WRONG_APPLICATION_DESCRIPTION);
        }
        if (applicationDtoRequest.getProfiles()==null || applicationDtoRequest.getProfiles().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_APPLICATION_PROFILES);
        }
    }

}
