package net.thumbtack.school.competition.daoimpl;

import net.thumbtack.school.competition.dao.ApplicationDao;
import net.thumbtack.school.competition.database.DataBase;
import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.models.Application;
import net.thumbtack.school.competition.models.Expert;

import java.util.List;
import java.util.Set;

public class ApplicationDaoImpl implements ApplicationDao {

    @Override
    public Integer addApplication(Application application) throws ServerException {
       return DataBase.getInstance().addApplication(application);
    }

    @Override
    public void removeApplication(Application application) throws ServerException {
        DataBase.getInstance().removeApplication(application);
    }

    @Override
    public Set<Integer> applicationsByProfiles(List<Integer> wishApplications) throws ServerException {
        return DataBase.getInstance().applicationsByProfiles(wishApplications);
    }

    @Override
    public void setMarkToApplication(Expert expert, int mark, Application application) throws ServerException {
        DataBase.getInstance().setMarkToApplication(expert,mark,application);
    }

    @Override
    public Application getApplicationById(int id) {
        return DataBase.getInstance().getApplicationById(id);
    }
}
