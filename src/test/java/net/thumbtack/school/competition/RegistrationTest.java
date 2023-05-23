package net.thumbtack.school.competition;

import com.google.gson.Gson;
// REVU тест это клиент
// клиент не может видеть внутренности сервиса
// поэтому здесь нельзя использовать сервисы, DAO, модель и исключения
// только server и json/DTO
// для очистки БД server.clear()
import net.thumbtack.school.competition.database.DataBase;
import net.thumbtack.school.competition.dto.request.ExpertDtoRequest;
import net.thumbtack.school.competition.dto.request.ParticipantDtoRequest;
import net.thumbtack.school.competition.server.Server;
import net.thumbtack.school.competition.server.ServerResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTest {
    private Gson gson = new Gson();
    private static final Server server = new Server();

    @Test
    public void testCorrectRegistrationParticipant(){
        ParticipantDtoRequest participantRegisterRequest = new ParticipantDtoRequest("Алексей Зубанков","ОМГУ","blabla@gmail.com","Stream@Java8");
        ServerResponse registerResponse = server.registerParticipant(gson.toJson(participantRegisterRequest));
        System.out.println(registerResponse.getResponseData());
        assertEquals(200,registerResponse.getResponseCode());
    }

    @Test
    public void testInvalidRegistrationParticipant(){
        ParticipantDtoRequest participantBadRegisterRequest = new ParticipantDtoRequest("Алексей Зубанков","ОМГУ","blabla@gmail.com","asd");
        assertEquals(400,server.registerParticipant(gson.toJson(participantBadRegisterRequest)).getResponseCode());
        ParticipantDtoRequest participant = new ParticipantDtoRequest("Иван Иванов","ОМГТУ","blabla@gmail.com","Stream@Java8");
        ParticipantDtoRequest participantCopy = new ParticipantDtoRequest("Иван Иванов","ОМГУ","blabla@gmail.com","Stream@Java8");
        assertEquals(200,server.registerParticipant(gson.toJson(participant)).getResponseCode());
        assertEquals(400,server.registerParticipant(gson.toJson(participantCopy)).getResponseCode());
    }

    @Test
    public void testCorrectRegistrationExpert(){
       ExpertDtoRequest expertRegisterRequest = new ExpertDtoRequest("Петр Петров", List.of(1),"blabla@gmail.com","Stream@Java8");
        ServerResponse registerResponse = server.registerExpert(gson.toJson(expertRegisterRequest));
        assertEquals(200,registerResponse.getResponseCode());
    }

    @Test
    public void testInvalidRegistrationExpert(){
        ExpertDtoRequest expertBadRegisterRequest = new ExpertDtoRequest("Иван Петров", List.of(2),"","Stream@Java8");
        ServerResponse registerResponse = server.registerExpert(gson.toJson(expertBadRegisterRequest));
        assertEquals(400,registerResponse.getResponseCode());
        ExpertDtoRequest expert =  new ExpertDtoRequest("Петр Иванов", List.of(2),"blabla@gmail.com","Stream@Java8");
        ExpertDtoRequest expertCopy = new ExpertDtoRequest("Петр Иванов", List.of(2),"blabla@gmail.com","Stream@Java8");
        assertEquals(200,server.registerExpert(gson.toJson(expert)).getResponseCode());
        assertEquals(400,server.registerExpert(gson.toJson(expertCopy)).getResponseCode());
    }

    @AfterEach
    public void clear(){
        DataBase.getInstance().Clear();
    }
}
