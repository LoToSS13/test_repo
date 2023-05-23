package net.thumbtack.school.competition.service;

import com.google.gson.Gson;
import net.thumbtack.school.competition.dao.UserDao;
import net.thumbtack.school.competition.daoimpl.UserDaoImpl;
import net.thumbtack.school.competition.dto.request.LoginDtoRequest;
import net.thumbtack.school.competition.dto.response.EmptyDtoResponse;
import net.thumbtack.school.competition.dto.response.UserDtoResponse;
import net.thumbtack.school.competition.exception.ErrorCode;
import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.models.User;
import net.thumbtack.school.competition.server.ResponseCode;
import net.thumbtack.school.competition.server.ServerResponse;

public class UserService extends ValidatingUtils {
    private final Gson gson = new Gson();
    private final UserDao dao = new UserDaoImpl();

    public ServerResponse login(String requestJsonString) {
        try {
            LoginDtoRequest loginDtoRequest = gson.fromJson(requestJsonString, LoginDtoRequest.class);

            validateUser(loginDtoRequest);

            User foundedUser = dao.getUser(loginDtoRequest.getLogin());
            validateActiveUser(loginDtoRequest, foundedUser);

            String token = dao.login(foundedUser);

            UserDtoResponse responseDto = new UserDtoResponse(token);
            return new ServerResponse(ResponseCode.SUCCESS.getCode(), gson.toJson(responseDto));

        } catch (ServerException e){

            return new ServerResponse(e);
        }
    }

    public ServerResponse logout(String token) {
        try{
            if (!validateInput(token)){

                throw new ServerException(ErrorCode.WRONG_JSON);
            }
            dao.logout(token);
            return new ServerResponse(ResponseCode.SUCCESS.getCode(),gson.toJson(new EmptyDtoResponse()));
        } catch (ServerException e){

            return new ServerResponse(e);
        }
    }

    public ServerResponse leave(String token) {
        try{
            if (!validateInput(token)){

                throw new ServerException(ErrorCode.WRONG_JSON);
            }
            dao.leave(token);
            return new ServerResponse(ResponseCode.SUCCESS.getCode(),gson.toJson(new EmptyDtoResponse()));
        } catch (ServerException e){

            return new ServerResponse(e);
        }
    }

    public static void validateUser(LoginDtoRequest loginDto) throws ServerException{
        if (!validateInput(loginDto.getLogin())){
            throw new ServerException(ErrorCode.WRONG_LOGIN);
        }
        if (!validateInput(loginDto.getPassword())){
            throw new ServerException(ErrorCode.WRONG_PASSWORD);
        }
    }

    public static void validateActiveUser(LoginDtoRequest user, User activeUser) throws ServerException {
        if (activeUser==null){
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }
        if (!activeUser.getPassword().equals(user.getPassword())){
            throw new ServerException(ErrorCode.WRONG_PASSWORD);
        }
    }
}
