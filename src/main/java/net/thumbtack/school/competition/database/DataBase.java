package net.thumbtack.school.competition.database;

import net.thumbtack.school.competition.exception.ErrorCode;

import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.models.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;


import java.util.*;

public class DataBase {

    public static DataBase dataBase;
    private int nextUserId = 1;
    private int nextApplicationId = 1;
    private int nextProfileId = 1;
    private Map<String, User> users = new HashMap<>();
    private BidiMap<String, User> activeUsers = new DualHashBidiMap<>();
    private Map<Integer,User> idToUser = new HashMap<>();
    private Map<Expert,Map<Application,Integer>> expertMarkedApplications = new HashMap<>();
    private MultiValuedMap<Participant, Application> participantApplications = new ArrayListValuedHashMap<>();
    private Map<Integer,Application> idToApplication = new HashMap<>();
    private MultiValuedMap<Integer,Integer> profileToApplication = new HashSetValuedHashMap<>();
    private Map<Integer, Profile> profiles = new HashMap<>();


    private DataBase(){

    }

    public static synchronized DataBase getInstance(){
        if (dataBase == null){
            dataBase = new DataBase();
        }
        return dataBase;
    }


    public void insertUser(User user) throws ServerException {
        if (users.putIfAbsent(user.getLogin(), user)!=null){
            throw new ServerException(ErrorCode.USER_ALREADY_EXIST);
        }
        user.setId(nextUserId);
        nextUserId++;
        idToUser.put(user.getId(),user);
    }
    public String insertActiveUser( User user) {
        String token = activeUsers.getKey(user);
        if (token==null) {
            token = UUID.randomUUID().toString();
            activeUsers.put(token, users.get(user.getLogin()));
        }
        return token;
    }

    public void logout(String token) throws ServerException{
        if (activeUsers.remove(token)==null){
            throw new ServerException(ErrorCode.USER_NOT_LOGIN);
        }
    }

    public void leave(String token) throws ServerException{
        User removed = activeUsers.remove(token);
        if (removed==null){
            throw new ServerException(ErrorCode.USER_NOT_LOGIN);
        }
        idToUser.remove(removed);
        if (removed.getClass().equals(Participant.class)){
            Collection<Application> applications = participantApplications.remove(removed);
            if (!applications.isEmpty()){
                for (Application tempApplication: applications){
                    idToApplication.remove(tempApplication.getId());
                }
            }
        } else{
            expertMarkedApplications.remove(removed);
        }
    }

    public User getUser(String login){
        return users.get(login);
    }
    public User getActiveUser(String token){
        return activeUsers.get(token);
    }
    public User getUserById(int id){
        return idToUser.get(id);
    }
    public Application getApplicationById(int id){
        return idToApplication.get(id);
    }

    public Integer addApplication(Application application) throws ServerException {
        participantApplications.put(application.getParticipant(), application);

        for (Integer tempProfile: application.getProfiles()){
            if (!profiles.containsKey(tempProfile)){
                throw new ServerException(ErrorCode.WRONG_PROFILE);
            }
            Profile parent = profiles.get(tempProfile).getParent();
            if (parent != null){
                profileToApplication.put(parent.getId(), nextApplicationId);
            }
            profileToApplication.put(tempProfile,nextApplicationId);


        }
        application.setId(nextApplicationId);

        idToApplication.put(application.getId(),application);

        return nextApplicationId++;
    }
    public void removeApplication(Application application) throws ServerException{
        Collection<Application> ApplicationCollection = participantApplications.get(application.getParticipant());
        if (!ApplicationCollection.contains(application)){
            throw new ServerException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        participantApplications.removeMapping(application.getParticipant(),application);
        for (Integer tempProfile: application.getProfiles()){
            profileToApplication.removeMapping(tempProfile,application);
        }
    }

    public Set<Integer> applicationsByProfiles(List<Integer> wishProfiles) {
        Set<Integer> foundedApplications = new HashSet<>();
        for (Integer tempWishedProfile: wishProfiles){
            foundedApplications.addAll(profileToApplication.get(tempWishedProfile));
        }

        return foundedApplications;
    }

    public void setMarkToApplication(Expert expert, int mark, Application application){
        Map<Application, Integer> markedApplication = expertMarkedApplications.get(expert);
        if (markedApplication==null){
            markedApplication = new HashMap<>();
        }
        markedApplication.put(application,mark);
        expertMarkedApplications.put(expert, markedApplication);

    }

    public List<Application> sortApplications(Fund fund){
        Map<Application, List<Integer>> applicationListHashMap = new HashMap<>();
        for (Map.Entry<Expert, Map<Application, Integer>> entry : expertMarkedApplications.entrySet()){
            for (Map.Entry<Application, Integer> temp : entry.getValue().entrySet()){
                List<Integer> listWithMarks;
                if (applicationListHashMap.containsKey(temp.getKey())){
                    listWithMarks = applicationListHashMap.get(temp.getKey());
                } else{
                    listWithMarks = new ArrayList<>();
                }
                listWithMarks.add(temp.getValue());
                applicationListHashMap.put(temp.getKey(),listWithMarks);
            }
        }

        Map<Application, Double> ApplicationToAverageMark = new HashMap<>();
        for (Map.Entry<Application, List<Integer>> temp : applicationListHashMap.entrySet()){
            int summOfMarks = 0;
            for (Integer tempInt: temp.getValue()){
                summOfMarks+=tempInt;
            }
            double average = summOfMarks/temp.getValue().size();
            ApplicationToAverageMark.put(temp.getKey(), average);
        }

        List<Map.Entry<Application, Double>> list = new LinkedList<>(ApplicationToAverageMark.entrySet());
        list.sort((o1, o2) -> {
            if (o1.getValue().compareTo(o2.getValue()) == 0) {
                if (o1.getKey().getAmount() > o2.getKey().getAmount()) {
                    return 1;
                }
                return -1;
            }
            return (o1.getValue().compareTo(o2.getValue())) * -1;
        });
        List<Application> sorted = new ArrayList<>();
        for (Map.Entry<Application, Double> entry : list)
        {
            if (fund.getAmount()>entry.getKey().getAmount() && entry.getValue().compareTo(fund.getCriticalMark())!=-1){
                sorted.add(entry.getKey());
                fund.setAmount(fund.getAmount()-entry.getKey().getAmount());
            }
        }
        return sorted;
    }

    public Integer addProfile(Integer parentalProfileId, String explanation) throws ServerException {
        if (parentalProfileId != 0 && !profiles.containsKey(parentalProfileId)){
            throw new ServerException(ErrorCode.WRONG_PARENTAL_ID);
        }

        if (parentalProfileId == 0){
            profiles.put(nextProfileId, new Profile(nextProfileId, explanation, null ));
        }else {
            Profile parentalProfile = profiles.get(parentalProfileId);
            profiles.put(nextProfileId, new Profile(nextProfileId, explanation, parentalProfile ));
        }

        return nextProfileId++;
    }

    public void Clear(){
        users.clear();
        activeUsers.clear();
        participantApplications.clear();
        expertMarkedApplications.clear();
        profileToApplication.clear();
        idToApplication.clear();
        idToUser.clear();
        nextApplicationId = 1;
        nextProfileId = 1;
        nextUserId = 1;
        profiles.clear();

    }
}
