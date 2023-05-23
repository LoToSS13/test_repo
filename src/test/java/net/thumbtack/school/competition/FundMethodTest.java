package net.thumbtack.school.competition;

import com.google.gson.Gson;

import net.thumbtack.school.competition.dto.request.*;
import net.thumbtack.school.competition.dto.response.FundDtoResponse;

import net.thumbtack.school.competition.dto.response.UserDtoResponse;
import net.thumbtack.school.competition.server.Server;
import net.thumbtack.school.competition.server.ServerResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FundMethodTest {

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

        ParticipantDtoRequest participantRequestFirst = new ParticipantDtoRequest("Алексей Зубанков","ОМГУ","zubankov@gmail.com","Stream@Java8");
        ParticipantDtoRequest participantRequestSecond = new ParticipantDtoRequest("Петр Петров","ОМГУ","petrov@inbox.ru","Stream@Java8");
        server.registerParticipant(gson.toJson(participantRequestFirst));
        server.registerParticipant(gson.toJson(participantRequestSecond));

        LoginDtoRequest participantLoginFirst = new LoginDtoRequest("zubankov@gmail.com","Stream@Java8");
        LoginDtoRequest participantLoginSecond = new LoginDtoRequest("petrov@inbox.ru","Stream@Java8");
        String tokenParticipantFirst = gson.fromJson(server.login(gson.toJson(participantLoginFirst)).getResponseData(), UserDtoResponse.class).getToken();
        String tokenParticipantSecond = gson.fromJson(server.login(gson.toJson(participantLoginSecond)).getResponseData(), UserDtoResponse.class).getToken();

        ApplicationDtoRequest firstApplication = new ApplicationDtoRequest("11 блок Серверы","Пишим сервер", List.of(1),5);
        ApplicationDtoRequest secondApplication = new ApplicationDtoRequest("Философия искусства в обществе","что-то умное",Arrays.asList(2, 3), 15000);
        ApplicationDtoRequest thirdApplication = new ApplicationDtoRequest("Искусство программирования","что-то умное",Arrays.asList(2,1),1000);
        ApplicationDtoRequest fourthApplication = new ApplicationDtoRequest("Экологичное общество","Мы живем в....",Arrays.asList(2,5,6),100000);
        ApplicationDtoRequest fifthApplication = new ApplicationDtoRequest("Математика в программировании","Для оптимизаци..",Arrays.asList(7,8),999);

        server.makeApplication(tokenParticipantFirst, gson.toJson(firstApplication));
        server.makeApplication(tokenParticipantFirst, gson.toJson(secondApplication));
        server.makeApplication(tokenParticipantFirst, gson.toJson(thirdApplication));
        server.makeApplication(tokenParticipantSecond,gson.toJson(fourthApplication));
        server.makeApplication(tokenParticipantSecond,gson.toJson(fifthApplication));

        ExpertDtoRequest expertRequestFirst = new ExpertDtoRequest("Павел Дворкин", Arrays.asList(1,2,3),"dvorkin@gmail.com","Stream@Java8");
        ExpertDtoRequest expertRequestSecond = new ExpertDtoRequest("Иван Иванов", Arrays.asList(3, 4,5),"Ivanov@gmail.com","Stream@Java8");
        ExpertDtoRequest expertRequestThird = new ExpertDtoRequest("Светлана Светлая", Arrays.asList(6,7,8),"Svetlana@mail.ru","Stream@Java8");

        server.registerExpert(gson.toJson(expertRequestFirst));
        server.registerExpert(gson.toJson(expertRequestSecond));
        server.registerExpert(gson.toJson(expertRequestThird));

        LoginDtoRequest expertLoginFirst = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");
        LoginDtoRequest expertLoginSecond = new LoginDtoRequest("Ivanov@gmail.com","Stream@Java8");
        LoginDtoRequest expertLoginThird = new LoginDtoRequest("Svetlana@mail.ru","Stream@Java8");

        String tokenExpertFirst = gson.fromJson(server.login(gson.toJson(expertLoginFirst)).getResponseData(), UserDtoResponse.class).getToken();
        String tokenExpertSecond = gson.fromJson(server.login(gson.toJson(expertLoginSecond)).getResponseData(), UserDtoResponse.class).getToken();
        String tokenExpertThird = gson.fromJson(server.login(gson.toJson(expertLoginThird)).getResponseData(), UserDtoResponse.class).getToken();

        MarkApplicationDtoRequest firstMarkedApplication1 = new MarkApplicationDtoRequest(firstApplication,5);
        MarkApplicationDtoRequest firstMarkedApplication2 = new MarkApplicationDtoRequest(firstApplication, 5);
        MarkApplicationDtoRequest secondMarkedApplication = new MarkApplicationDtoRequest(secondApplication,3);
        MarkApplicationDtoRequest thirdMarkedApplication1 = new MarkApplicationDtoRequest(thirdApplication, 4);
        MarkApplicationDtoRequest thirdMarkedApplication2 = new MarkApplicationDtoRequest(thirdApplication, 4);
        MarkApplicationDtoRequest fourthMarkedApplication = new MarkApplicationDtoRequest(fourthApplication,2);
        MarkApplicationDtoRequest fifthMarkedApplication1 = new MarkApplicationDtoRequest(fifthApplication, 3);

        server.setMarkToApplication(tokenExpertFirst, gson.toJson(firstMarkedApplication1));
        server.setMarkToApplication(tokenExpertFirst, gson.toJson(thirdMarkedApplication1));
        server.setMarkToApplication(tokenExpertFirst, gson.toJson(fifthMarkedApplication1));

        server.setMarkToApplication(tokenExpertSecond, gson.toJson(firstMarkedApplication2));
        server.setMarkToApplication(tokenExpertSecond, gson.toJson(secondMarkedApplication));
        server.setMarkToApplication(tokenExpertSecond, gson.toJson(thirdMarkedApplication2));

        server.setMarkToApplication(tokenExpertThird,gson.toJson(fourthMarkedApplication));
    }

    @Test
    public void fundSumTest(){
        FundDtoRequest fundDto = new FundDtoRequest(30000,0.1);
        ServerResponse fundResponse = server.summarizingApplications(gson.toJson(fundDto));

        FundDtoResponse fundDtoResponse = gson.fromJson(fundResponse.getResponseData(), FundDtoResponse.class);

        assertEquals(200,fundResponse.getResponseCode());
        assertEquals(4, fundDtoResponse.getApplicationRating().size());

        List<ApplicationDtoRequest> result = new LinkedList<>();
        result.add(new ApplicationDtoRequest("11 блок Серверы","Пишим сервер", Arrays.asList(1),5));
        result.add(new ApplicationDtoRequest("Искусство программирования","что-то умное",Arrays.asList(2,1),1000));
        result.add(new ApplicationDtoRequest( "Математика в программировании","Для оптимизаци..",Arrays.asList(7,8),999));
        result.add(new ApplicationDtoRequest("Философия искусства в обществе","что-то умное",Arrays.asList(2, 3), 15000));

        assertEquals(result, fundDtoResponse.getApplicationRating());
    }

    @Test
    public void invalidFundSumTest(){
        FundDtoRequest invalidMoneyFundDto = new FundDtoRequest(-5000,0.2);
        FundDtoRequest invalidCritMarkFundDto = new FundDtoRequest(353535,-1.0);

        ServerResponse invalidMoneyFundResponse = server.summarizingApplications(gson.toJson(invalidMoneyFundDto));
        ServerResponse invaliCritMarkFundResponse = server.summarizingApplications(gson.toJson(invalidCritMarkFundDto));

        assertEquals(400, invalidMoneyFundResponse.getResponseCode());

        assertEquals(400,invaliCritMarkFundResponse.getResponseCode());
    }
    @AfterEach
    public void clear(){
        server.clear();
    }
}
