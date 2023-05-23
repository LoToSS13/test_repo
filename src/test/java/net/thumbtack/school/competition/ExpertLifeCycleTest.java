package net.thumbtack.school.competition;

import com.google.gson.Gson;
import net.thumbtack.school.competition.dto.request.*;
import net.thumbtack.school.competition.dto.response.SortedApplicationDtoResponse;
import net.thumbtack.school.competition.dto.response.UserDtoResponse;
import net.thumbtack.school.competition.server.Server;
import net.thumbtack.school.competition.server.ServerResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpertLifeCycleTest {
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

        LoginDtoRequest participantLogin = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        String tokenParticipant = gson.fromJson(server.login(gson.toJson(participantLogin)).getResponseData(), UserDtoResponse.class).getToken();

        ApplicationDtoRequest firstApplication = new ApplicationDtoRequest("11 блок Серверы","Пишим сервер", Arrays.asList(1),5);
        ApplicationDtoRequest secondApplication = new ApplicationDtoRequest("Философия искусства и общества","что-то умное",Arrays.asList(2, 3), 15);
        ApplicationDtoRequest thirdApplication = new ApplicationDtoRequest("Искусство программирования","что-то умное",Arrays.asList(2,1),10);

        server.makeApplication(tokenParticipant, gson.toJson(firstApplication));
        server.makeApplication(tokenParticipant, gson.toJson(secondApplication));
        server.makeApplication(tokenParticipant, gson.toJson(thirdApplication));

        ExpertDtoRequest expertRequestFirst = new ExpertDtoRequest("Павел Дворкин", Arrays.asList(1),"dvorkin@gmail.com","Stream@Java8");
        ExpertDtoRequest expertRequestSecond = new ExpertDtoRequest("Иван Иванов", Arrays.asList(1, 2),"Ivanov@gmail.com","Stream@Java8");
        server.registerExpert(gson.toJson(expertRequestFirst));
        server.registerExpert(gson.toJson(expertRequestSecond));
    }

    @Test
    public void getApplicationByProfileTest(){
        LoginDtoRequest expertFirst = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");
        LoginDtoRequest expertSecond = new LoginDtoRequest("Ivanov@gmail.com","Stream@Java8");
        String tokenExpertFirst = gson.fromJson(server.login(gson.toJson(expertFirst)).getResponseData(), UserDtoResponse.class).getToken();
        String tokenExpertSecond = gson.fromJson(server.login(gson.toJson(expertSecond)).getResponseData(),UserDtoResponse.class).getToken();

        SortedApplicationDtoRequest wishProfilesByFirst = new SortedApplicationDtoRequest(Arrays.asList(1));
        SortedApplicationDtoRequest wishProfilesBySecond = new SortedApplicationDtoRequest(Arrays.asList(2,1));

        ServerResponse grantsToFirstExpert = server.getApplicationsByProfiles(tokenExpertFirst,gson.toJson(wishProfilesByFirst));
        ServerResponse grantsToSecondExpert = server.getApplicationsByProfiles(tokenExpertSecond,gson.toJson(wishProfilesBySecond));

        assertEquals(200, grantsToFirstExpert.getResponseCode());
        assertEquals(200, grantsToSecondExpert.getResponseCode());

        assertEquals(2, gson.fromJson(grantsToFirstExpert.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs().size());
        assertEquals(3, gson.fromJson(grantsToSecondExpert.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs().size());
    }

    @Test
    public void invalidGetApplicationByProfileTest(){
        LoginDtoRequest expertFirst = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");
        String tokenExpertFirst = gson.fromJson(server.login(gson.toJson(expertFirst)).getResponseData(), UserDtoResponse.class).getToken();

        SortedApplicationDtoRequest wrongWishProfilesByFirst = new SortedApplicationDtoRequest(Arrays.asList(4));
        SortedApplicationDtoRequest wishProfilesBySecond = new SortedApplicationDtoRequest(Arrays.asList(5,6));

        ServerResponse wrongProfilesByFirst = server.getApplicationsByProfiles(tokenExpertFirst,gson.toJson(wrongWishProfilesByFirst));
        ServerResponse wrongTokenBySecond = server.getApplicationsByProfiles("InVaLiD_ToKeN", gson.toJson(wishProfilesBySecond));

        assertEquals(400,wrongProfilesByFirst.getResponseCode());
        assertEquals(400,wrongTokenBySecond.getResponseCode());
    }

    @Test
    public void markApplicationTest(){
        LoginDtoRequest expert = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");
        String tokenExpert = gson.fromJson(server.login(gson.toJson(expert)).getResponseData(), UserDtoResponse.class).getToken();

        MarkApplicationDtoRequest markedApplication = new MarkApplicationDtoRequest(new ApplicationDtoRequest("11 блок Серверы","Пишим сервер", Arrays.asList(1),5),5);

        ServerResponse markApplication = server.setMarkToApplication(tokenExpert,gson.toJson(markedApplication));

        assertEquals(200,markApplication.getResponseCode());
    }

    @Test
    public void invalidMarkApplicationTest(){
        LoginDtoRequest expertFirst = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");
        String tokenExpertFirst = gson.fromJson(server.login(gson.toJson(expertFirst)).getResponseData(), UserDtoResponse.class).getToken();

        MarkApplicationDtoRequest invalidMarkMarkedApplication = new MarkApplicationDtoRequest(new ApplicationDtoRequest("11 блок Серверы","Пишим сервер", Arrays.asList(1),5),-1);
        MarkApplicationDtoRequest markedApplication = new MarkApplicationDtoRequest(new ApplicationDtoRequest("11 блок Серверы","Пишим сервер", Arrays.asList(1),5),5);

        ServerResponse invalidMarkResponse = server.setMarkToApplication(tokenExpertFirst,gson.toJson(invalidMarkMarkedApplication));
        ServerResponse invalidTokenResponse = server.setMarkToApplication("InValId_ToKEn",gson.toJson(markedApplication));

        assertEquals(400,invalidMarkResponse.getResponseCode());
        assertEquals(400,invalidTokenResponse.getResponseCode());
    }
    @AfterEach
    public void clear(){
        server.clear();
    }
}
