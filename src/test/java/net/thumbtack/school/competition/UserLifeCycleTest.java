package net.thumbtack.school.competition;

import com.google.gson.Gson;
import net.thumbtack.school.competition.dto.request.*;
import net.thumbtack.school.competition.dto.response.UserDtoResponse;
import net.thumbtack.school.competition.server.Server;
import net.thumbtack.school.competition.server.ServerResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserLifeCycleTest {
    private Gson gson = new Gson();
    private static final Server server = new Server();

    @BeforeEach
    public void register(){
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(0,"Физика");
        AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(0, "Математика");
        AddProfileDtoRequest addProfileDtoRequest2 = new AddProfileDtoRequest(0, "Информатика");
        AddProfileDtoRequest addProfileDtoRequest3 = new AddProfileDtoRequest(1,"Общие вопросы ");
        AddProfileDtoRequest addProfileDtoRequest4 = new AddProfileDtoRequest(2, "Углубленное изучение");
        AddProfileDtoRequest addProfileDtoRequest5 = new AddProfileDtoRequest(1, "Компьютерные науки");

        server.addProfile(gson.toJson(addProfileDtoRequest));
        server.addProfile(gson.toJson(addProfileDtoRequest1));
        server.addProfile(gson.toJson(addProfileDtoRequest2));
        server.addProfile(gson.toJson(addProfileDtoRequest3));
        server.addProfile(gson.toJson(addProfileDtoRequest4));
        server.addProfile(gson.toJson(addProfileDtoRequest5));

        ParticipantDtoRequest participantRequest = new ParticipantDtoRequest("Алексей Зубанков","ОМГУ","zubankov@gmail.com","Stream@Java8");
        server.registerParticipant(gson.toJson(participantRequest));
        ExpertDtoRequest expertRequest = new ExpertDtoRequest("Павел Дворкин", Arrays.asList(1),"dvorkin@gmail.com","Stream@Java8");
        server.registerExpert(gson.toJson(expertRequest));
    }

    @Test
    public void loginTest(){
        LoginDtoRequest userLoginFirst = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        ServerResponse responseFirst = server.login(gson.toJson(userLoginFirst));
        LoginDtoRequest userLoginSecond = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");
        ServerResponse responseSecond = server.login(gson.toJson(userLoginSecond));
        assertEquals(200, responseFirst.getResponseCode());
        assertEquals(200, responseSecond.getResponseCode());
        String tokenParticipant = gson.fromJson(responseFirst.getResponseData(), UserDtoResponse.class).getToken();
        String tokenExpert =  gson.fromJson(responseSecond.getResponseData(),UserDtoResponse.class).getToken();
        assertFalse(tokenParticipant.equals(tokenExpert));
    }

    @Test
    public void loginInvalidTest() {
        LoginDtoRequest userLoginFirst = new LoginDtoRequest("","Stream@Java8");
        ServerResponse responseFirst = server.login(gson.toJson(userLoginFirst));
        LoginDtoRequest userLoginSecond = new LoginDtoRequest("dvorkin@gmail.com","");
        ServerResponse responseSecond = server.login(gson.toJson(userLoginSecond));
        LoginDtoRequest userLoginThird = new LoginDtoRequest("zubankov@gmail.com","Stream@Java999");
        ServerResponse responseThird = server.login(gson.toJson(userLoginThird));
        assertEquals(400,responseFirst.getResponseCode());
        assertEquals(400,responseSecond.getResponseCode());
        assertEquals(400,responseThird.getResponseCode());
    }

    @Test
    public void leaveTest() {
        LoginDtoRequest userLoginFirst = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        LoginDtoRequest userLoginSecond = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");

        String tokenParticipant = gson.fromJson(server.login(gson.toJson(userLoginFirst)).getResponseData(), UserDtoResponse.class).getToken();
        String tokenExpert = gson.fromJson(server.login(gson.toJson(userLoginSecond)).getResponseData(), UserDtoResponse.class).getToken();

        ServerResponse leaveExpert = server.leave(tokenExpert);
        ServerResponse leaveParticipant = server.leave(tokenParticipant);

        assertEquals(200, leaveExpert.getResponseCode());
        assertEquals(200, leaveParticipant.getResponseCode());
    }

    @Test
    public void invalidLeaveTest(){
        LoginDtoRequest userLoginFirst = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        LoginDtoRequest userLoginSecond = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");

        String tokenParticipant = gson.fromJson(server.login(gson.toJson(userLoginFirst)).getResponseData(), UserDtoResponse.class).getToken();
        String tokenExpert = gson.fromJson(server.login(gson.toJson(userLoginSecond)).getResponseData(), UserDtoResponse.class).getToken();

        ServerResponse leaveExpert = server.leave(tokenExpert);
        ServerResponse leaveParticipant = server.leave(tokenParticipant);

        assertEquals(200,leaveExpert.getResponseCode());
        assertEquals(200,leaveParticipant.getResponseCode());

        ServerResponse invalidResponseExpert = server.leave(tokenExpert);
        ServerResponse invalidResponseParticipant= server.leave(tokenParticipant);

        assertEquals(400,invalidResponseParticipant.getResponseCode());
        assertEquals(400,invalidResponseExpert.getResponseCode());
    }

    @Test
    public void logoutTest(){
        LoginDtoRequest userLoginFirst = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        LoginDtoRequest userLoginSecond = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");

        String tokenParticipant = gson.fromJson(server.login(gson.toJson(userLoginFirst)).getResponseData(), UserDtoResponse.class).getToken();
        String tokenExpert = gson.fromJson(server.login(gson.toJson(userLoginSecond)).getResponseData(), UserDtoResponse.class).getToken();

        ServerResponse logoutParticipant = server.logout(tokenParticipant);
        ServerResponse logoutExpert  = server.logout(tokenExpert);

        assertEquals(200,logoutExpert.getResponseCode());
        assertEquals(200,logoutParticipant.getResponseCode());
    }

    @Test
    public void invalidLogoutTest(){
        LoginDtoRequest userLoginFirst = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        LoginDtoRequest userLoginSecond = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");

        String tokenParticipant = gson.fromJson(server.login(gson.toJson(userLoginFirst)).getResponseData(), UserDtoResponse.class).getToken();
        String tokenExpert = gson.fromJson(server.login(gson.toJson(userLoginSecond)).getResponseData(), UserDtoResponse.class).getToken();

        server.logout(tokenParticipant);
        server.logout(tokenExpert);

        ServerResponse logoutParticipantInvalidToken = server.logout(tokenParticipant);
        ServerResponse logoutExpertInvalidToken = server.logout(tokenExpert);

        assertEquals(400, logoutExpertInvalidToken.getResponseCode());
        assertEquals(400, logoutParticipantInvalidToken.getResponseCode());
    }


    @AfterEach
    public void clear(){
        server.clear();
    }
}
