package net.thumbtack.school.competition.daoimpl;

import net.thumbtack.school.competition.dao.UserDao;
import net.thumbtack.school.competition.database.DataBase;
import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.models.User;

public class UserDaoImpl implements UserDao {

    @Override
    public String login(User user) throws ServerException {
        return DataBase.getInstance().insertActiveUser(user);
    }

    @Override
    public void logout(String token) throws ServerException {
        DataBase.getInstance().logout(token);
    }

    @Override
    public void leave(String token) throws ServerException {
        DataBase.getInstance().leave(token);
    }

    @Override
    public void insertToDatabase(User user) throws ServerException {
         DataBase.getInstance().insertUser(user);
    }

    @Override
    public User getActiveUser(String token) {
        return DataBase.getInstance().getActiveUser(token);
    }

    @Override
    public User getUser(String login) {
        return DataBase.getInstance().getUser(login);
    }

    @Override
    public User getUserById(int id) {
        return DataBase.getInstance().getUserById(id);
    }
}
