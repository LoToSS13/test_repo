package net.thumbtack.school.competition.dao;

import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.models.User;

public interface UserDao {
    String login(User user) throws ServerException;
    void logout(String token)throws ServerException;
    void leave(String token)throws ServerException;
    void insertToDatabase(User user) throws ServerException;
    User getActiveUser(String token);
    User getUser(String login);
    User getUserById(int id);
}
