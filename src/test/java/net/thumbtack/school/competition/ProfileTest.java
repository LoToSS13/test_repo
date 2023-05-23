package net.thumbtack.school.competition;

import com.google.gson.Gson;

import net.thumbtack.school.competition.dto.request.*;
import net.thumbtack.school.competition.dto.response.IdDtoResponse;
import net.thumbtack.school.competition.dto.response.SortedApplicationDtoResponse;
import net.thumbtack.school.competition.dto.response.UserDtoResponse;
import net.thumbtack.school.competition.server.Server;

import net.thumbtack.school.competition.server.ServerResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileTest {
    private Gson gson = new Gson();
    private static final Server server = new Server();

    @Test
    @BeforeEach
    public void addProfiles(){
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(0,"Физика");
        AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(0, "Математика");
        AddProfileDtoRequest addProfileDtoRequest2 = new AddProfileDtoRequest(0, "Информатика");

        ServerResponse response =  server.addProfile(gson.toJson(addProfileDtoRequest));
        ServerResponse response1 =  server.addProfile(gson.toJson(addProfileDtoRequest1));
        ServerResponse response2 =  server.addProfile(gson.toJson(addProfileDtoRequest2));

        assertEquals(200,response.getResponseCode());
        assertEquals(200,response1.getResponseCode());
        assertEquals(200,response2.getResponseCode());
    }

    @Test
    public void invalidAddProfiles(){
        String string = null;
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(0, null);
        AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(999, "PE");

        ServerResponse response =  server.addProfile(gson.toJson(addProfileDtoRequest));
        ServerResponse response1 =  server.addProfile(gson.toJson(string));
        ServerResponse response2 =  server.addProfile(gson.toJson(addProfileDtoRequest1));

        assertEquals(400,response.getResponseCode());
        assertEquals(400,response1.getResponseCode());
        assertEquals(400,response2.getResponseCode());
    }

    @Test
    public void addSubProfiles(){
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(1,"Общие вопросы ");
        AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(2, "Углубленное изучение");
        AddProfileDtoRequest addProfileDtoRequest2 = new AddProfileDtoRequest(1, "Компьютерные науки");

        ServerResponse response =  server.addProfile(gson.toJson(addProfileDtoRequest));
        ServerResponse response1 =  server.addProfile(gson.toJson(addProfileDtoRequest1));
        ServerResponse response2 =  server.addProfile(gson.toJson(addProfileDtoRequest2));

        assertEquals(200,response.getResponseCode());
        assertEquals(200,response1.getResponseCode());
        assertEquals(200,response2.getResponseCode());
    }

    @Test
    public void controlTest(){
        AddProfileDtoRequest addProfileDtoRequestP1 = new AddProfileDtoRequest(0,"Физика");
        AddProfileDtoRequest addProfileDtoRequestP2 = new AddProfileDtoRequest(0, "Математика");
        AddProfileDtoRequest addProfileDtoRequestP3 = new AddProfileDtoRequest(0, "Информатика");


        ServerResponse response1 = server.addProfile(gson.toJson(addProfileDtoRequestP1));
        ServerResponse response2 =server.addProfile(gson.toJson(addProfileDtoRequestP2));
        ServerResponse response3 = server.addProfile(gson.toJson(addProfileDtoRequestP3));

        IdDtoResponse idDtoResponseP1 =  gson.fromJson(response1.getResponseData(), IdDtoResponse.class);
        IdDtoResponse idDtoResponseP2 =  gson.fromJson(response2.getResponseData(), IdDtoResponse.class);
        IdDtoResponse idDtoResponseP3 =  gson.fromJson(response3.getResponseData(), IdDtoResponse.class);

        Integer p1 = idDtoResponseP1.getId();
        Integer p2 = idDtoResponseP2.getId();
        Integer p3 = idDtoResponseP3.getId();


        AddProfileDtoRequest addProfileDtoRequestP1S1= new AddProfileDtoRequest(p1,"Общая");
        AddProfileDtoRequest addProfileDtoRequestP1S2 = new AddProfileDtoRequest(p1, "Углубленная");
        AddProfileDtoRequest addProfileDtoRequestP3S1 = new AddProfileDtoRequest(p3,"ИКТ");
        AddProfileDtoRequest addProfileDtoRequestP3S2 = new AddProfileDtoRequest(p3, "CS");
        AddProfileDtoRequest addProfileDtoRequestP3S13 = new AddProfileDtoRequest(p3, "Network");


        ServerResponse response4 = server.addProfile(gson.toJson(addProfileDtoRequestP1S1));
        ServerResponse response5 =server.addProfile(gson.toJson(addProfileDtoRequestP1S2));
        ServerResponse response6 = server.addProfile(gson.toJson(addProfileDtoRequestP3S1));
        ServerResponse response7 = server.addProfile(gson.toJson(addProfileDtoRequestP3S2));
        ServerResponse response8 =server.addProfile(gson.toJson(addProfileDtoRequestP3S13));


        IdDtoResponse idDtoResponseP1S1 =  gson.fromJson(response4.getResponseData(), IdDtoResponse.class);
        IdDtoResponse idDtoResponseP1S2 =  gson.fromJson(response5.getResponseData(), IdDtoResponse.class);
        IdDtoResponse idDtoResponseP3S1 =  gson.fromJson(response6.getResponseData(), IdDtoResponse.class);
        IdDtoResponse idDtoResponseP3S2 =  gson.fromJson(response7.getResponseData(), IdDtoResponse.class);
        IdDtoResponse idDtoResponseP3S3 =  gson.fromJson(response8.getResponseData(), IdDtoResponse.class);

        Integer p1s1 = idDtoResponseP1S1.getId();
        Integer p1s2 = idDtoResponseP1S2.getId();
        Integer p3s1 = idDtoResponseP3S1.getId();
        Integer p3s2 = idDtoResponseP3S2.getId();
        Integer p3s3 = idDtoResponseP3S3.getId();


        // Создаем Participant, чтобы зарегистрировать заявку
        ParticipantDtoRequest participantRegisterRequest = new ParticipantDtoRequest("Алексей Зубанков","ОМГУ","blabla@gmail.com","Stream@Java8");
        server.registerParticipant(gson.toJson(participantRegisterRequest));

        // Заходим на сервер за созданного Participant
        LoginDtoRequest participantLogin = new LoginDtoRequest("blabla@gmail.com","Stream@Java8");
        String tokenParticipant = gson.fromJson(server.login(gson.toJson(participantLogin)).getResponseData(), UserDtoResponse.class).getToken();

        // Создаем заявки
        ApplicationDtoRequest makeApplication = new ApplicationDtoRequest("Ядро","новая операционная система", Arrays.asList(p1, p2, p1s2, p3s3),5);
        ApplicationDtoRequest makeApplication1 = new ApplicationDtoRequest("Мир","новая платежная система", Arrays.asList(p1, p1s2, p3s3),5);

        ServerResponse response9 =server.makeApplication(tokenParticipant, gson.toJson(makeApplication));
        ServerResponse response10 =server.makeApplication(tokenParticipant, gson.toJson(makeApplication1));

        IdDtoResponse idDtoResponseA1 =  gson.fromJson(response9.getResponseData(), IdDtoResponse.class);
        IdDtoResponse idDtoResponseA2 =  gson.fromJson(response10.getResponseData(), IdDtoResponse.class);

        Integer a1 = idDtoResponseA1.getId();
        Integer a2 = idDtoResponseA2.getId();

        System.out.println(a1);
        System.out.println(a2);
        // Регистрируем эксперта
        ExpertDtoRequest expertRequestFirst = new ExpertDtoRequest("Павел Дворкин", Arrays.asList(p1,p2,p3,p1s1,p1s2,p3s1,p3s2,p3s3),"dvorkin@gmail.com","Stream@Java8");
        server.registerExpert(gson.toJson(expertRequestFirst));

        // Логинимся за эксперта
        LoginDtoRequest expertLogin = new LoginDtoRequest("dvorkin@gmail.com","Stream@Java8");
        String expertToken = gson.fromJson(server.login(gson.toJson(expertLogin)).getResponseData(), UserDtoResponse.class).getToken();

        // Запрашиваем для каждого направления список заявок
        SortedApplicationDtoRequest wishProfiles = new SortedApplicationDtoRequest(List.of(p1));
        SortedApplicationDtoRequest wishProfiles1 = new SortedApplicationDtoRequest(List.of(p2));
        SortedApplicationDtoRequest wishProfiles2 = new SortedApplicationDtoRequest(List.of(p3));
        SortedApplicationDtoRequest wishProfiles7 = new SortedApplicationDtoRequest(List.of(p1s1));
        SortedApplicationDtoRequest wishProfiles8 = new SortedApplicationDtoRequest(List.of(p1s2));
        SortedApplicationDtoRequest wishProfiles9 = new SortedApplicationDtoRequest(List.of(p3s1));
        SortedApplicationDtoRequest wishProfiles10 = new SortedApplicationDtoRequest(List.of(p3s2));
        SortedApplicationDtoRequest wishProfiles11 = new SortedApplicationDtoRequest(List.of(p3s3));


        ServerResponse grantsToFirstExpert = server.getApplicationsByProfiles(expertToken,gson.toJson(wishProfiles));
        ServerResponse grantsToFirstExpert1 = server.getApplicationsByProfiles(expertToken,gson.toJson(wishProfiles1));
        ServerResponse grantsToFirstExpert2 = server.getApplicationsByProfiles(expertToken,gson.toJson(wishProfiles2));
        ServerResponse grantsToFirstExpert7 = server.getApplicationsByProfiles(expertToken,gson.toJson(wishProfiles7));
        ServerResponse grantsToFirstExpert8 = server.getApplicationsByProfiles(expertToken,gson.toJson(wishProfiles8));
        ServerResponse grantsToFirstExpert9 = server.getApplicationsByProfiles(expertToken,gson.toJson(wishProfiles9));
        ServerResponse grantsToFirstExpert10 = server.getApplicationsByProfiles(expertToken,gson.toJson(wishProfiles10));
        ServerResponse grantsToFirstExpert11 = server.getApplicationsByProfiles(expertToken,gson.toJson(wishProfiles11));

        assertEquals(Set.of(a1,a2), gson.fromJson(grantsToFirstExpert.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs());
        assertEquals(Set.of(a1), gson.fromJson(grantsToFirstExpert1.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs());
        assertEquals(Set.of(a1,a2), gson.fromJson(grantsToFirstExpert2.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs());
        assertEquals(0, gson.fromJson(grantsToFirstExpert7.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs().size());
        assertEquals(Set.of(a1,a2), gson.fromJson(grantsToFirstExpert8.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs());
        assertEquals(0, gson.fromJson(grantsToFirstExpert9.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs().size());
        assertEquals(0, gson.fromJson(grantsToFirstExpert10.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs().size());
        assertEquals(Set.of(a1,a2), gson.fromJson(grantsToFirstExpert11.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs());

        // Финальная проверка создаем новое направление
        AddProfileDtoRequest addProfileDtoRequestP4 = new AddProfileDtoRequest(0,"PE");
        ServerResponse response11 = server.addProfile(gson.toJson(addProfileDtoRequestP4));
        IdDtoResponse idDtoResponseP4 =  gson.fromJson(response11.getResponseData(), IdDtoResponse.class);
        Integer p4 = idDtoResponseP4.getId();


        ExpertDtoRequest expertRequest = new ExpertDtoRequest("Иван Иванов", Collections.singletonList(p4),"ivanov@gmail.com","Stream@Java8");
        server.registerExpert(gson.toJson(expertRequest));

        LoginDtoRequest expertLogin1 = new LoginDtoRequest("ivanov@gmail.com","Stream@Java8");
        String expertToken1 = gson.fromJson(server.login(gson.toJson(expertLogin1)).getResponseData(), UserDtoResponse.class).getToken();


        SortedApplicationDtoRequest wishProfilesP4 = new SortedApplicationDtoRequest(List.of(p4));
        ServerResponse response12 = server.getApplicationsByProfiles(expertToken1,gson.toJson(wishProfilesP4));

        assertEquals(0, gson.fromJson(response12.getResponseData(), SortedApplicationDtoResponse.class).getApplicationsIDs().size());
    }



    @AfterEach
    public void clear(){
        server.clear();
    }
}
