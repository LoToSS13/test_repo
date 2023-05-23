package net.thumbtack.school.competition.dao;

import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.models.Application;
import net.thumbtack.school.competition.models.Expert;

import java.util.List;
import java.util.Set;

public interface ApplicationDao {
    Integer addApplication( Application grant) throws ServerException;
    void removeApplication( Application grant) throws ServerException;
    Set<Integer> applicationsByProfiles(List<Integer> wishGrants) throws ServerException;
    void setMarkToApplication(Expert expert, int mark, Application grant)throws ServerException;
    Application getApplicationById(int id);
}
