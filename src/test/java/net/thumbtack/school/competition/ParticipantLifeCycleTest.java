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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticipantLifeCycleTest {

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
    }

    @Test
    public void makeApplicationTest(){
        LoginDtoRequest participantLogin = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        String tokenParticipant = gson.fromJson(server.login(gson.toJson(participantLogin)).getResponseData(), UserDtoResponse.class).getToken();

        ApplicationDtoRequest makeApplication = new ApplicationDtoRequest("Зачет","Серверы", Arrays.asList(1),5);
        ServerResponse makeApplicationResponse = server.makeApplication(tokenParticipant, gson.toJson(makeApplication));

        assertEquals(200, makeApplicationResponse.getResponseCode());
    }


    @Test
    public void invalidAddApplicationTest(){
        LoginDtoRequest participantLogin = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        String tokenParticipant = gson.fromJson(server.login(gson.toJson(participantLogin)).getResponseData(), UserDtoResponse.class).getToken();

        ApplicationDtoRequest makeApplicationFirst = new ApplicationDtoRequest("Зачет","", Arrays.asList(1),5);
        ApplicationDtoRequest makeApplicationSecond = new ApplicationDtoRequest("Зачет","Серверы", Arrays.asList(1),5);

        ServerResponse invalidDescriptionApplicationResponse = server.makeApplication(tokenParticipant,gson.toJson(makeApplicationFirst));
        ServerResponse correctApplicationResponse = server.makeApplication(tokenParticipant,gson.toJson(makeApplicationSecond));
        ServerResponse alreadyExistApplicationResponse = server.makeApplication(tokenParticipant,gson.toJson(makeApplicationSecond));
        ServerResponse invalidTokenResponse = server.makeApplication("InVaLiD_ToKen_WhIch_iS_NoT_ExIsT", gson.toJson(makeApplicationSecond));

        assertEquals(400,invalidDescriptionApplicationResponse.getResponseCode());

        assertEquals(200,correctApplicationResponse.getResponseCode());
        assertEquals(200,alreadyExistApplicationResponse.getResponseCode());

        assertEquals(400,invalidTokenResponse.getResponseCode());
    }

    @Test
    public void invalidRemoveApplicationTest(){
        LoginDtoRequest participantLogin = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        String tokenParticipant = gson.fromJson(server.login(gson.toJson(participantLogin)).getResponseData(), UserDtoResponse.class).getToken();

        ApplicationDtoRequest applicationFirst = new ApplicationDtoRequest("Зачет","Серверы", Arrays.asList(1),5);
        ApplicationDtoRequest applicationSecond = new ApplicationDtoRequest("Серверы","Зачет", Arrays.asList(1), 5);

        ServerResponse makeFirstApplication = server.makeApplication(tokenParticipant,gson.toJson(applicationFirst));
        ServerResponse removeFirstApplicationWithInvalidToken = server.deleteApplication("InVaLiD_ToKen",gson.toJson(applicationFirst));
        ServerResponse removeSecondApplicationWhichNotExist = server.deleteApplication(tokenParticipant, gson.toJson(applicationSecond));

        assertEquals(200,makeFirstApplication.getResponseCode());
        assertEquals(400,removeFirstApplicationWithInvalidToken.getResponseCode());

        assertEquals(400,removeSecondApplicationWhichNotExist.getResponseCode());
    }

    @AfterEach
    public void clear(){
        server.clear();
    }
}
