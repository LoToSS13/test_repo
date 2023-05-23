package net.thumbtack.school.competition.server;

import net.thumbtack.school.competition.service.*;

public class Server {
    private ExpertService expertService = new ExpertService();
    private ParticipantService participantService = new ParticipantService();
    private UserService userService = new UserService();
    private final FundService fundService = new FundService();
    private final DatabaseService databaseService = new DatabaseService();
    private final ProfileService profileService = new ProfileService();

    public ServerResponse registerParticipant(String requestJsonString){
        return participantService.register(requestJsonString);
    }
    public ServerResponse registerExpert(String requestJsonString){
        return expertService.register(requestJsonString);
    }

    public ServerResponse login(String requestJsonString){
        return userService.login(requestJsonString);
    }

    public ServerResponse logout(String requestJsonString){
        return userService.logout(requestJsonString);
    }
    public ServerResponse leave(String requestJsonString){
        return userService.leave(requestJsonString);
    }
    public ServerResponse makeApplication(String token, String requestJsonString) {
        return participantService.makeApplication(token,requestJsonString);
    }
    public ServerResponse deleteApplication(String token, String requestJsonString) {
        return participantService.deleteApplication(token,requestJsonString);
    }
    public ServerResponse getApplicationsByProfiles(String token, String requestJsonString) {
        return expertService.getApplicationsByProfiles(token, requestJsonString);
    }
    public ServerResponse setMarkToApplication(String token, String requestJsonString) {
        return expertService.markApplication(token,requestJsonString);
    }
    public ServerResponse summarizingApplications (String requestJsonString) {
        return fundService.summarizing(requestJsonString);
    }

    public void clear(){
        databaseService.clearDatabase();
    }

    public ServerResponse addProfile(String requestJsonString) {
        return profileService.addProfiles(requestJsonString);
    }
}
