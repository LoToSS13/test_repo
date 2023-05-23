package net.thumbtack.school.competition.daoimpl;

import net.thumbtack.school.competition.dao.ProfileDao;
import net.thumbtack.school.competition.database.DataBase;
import net.thumbtack.school.competition.exception.ServerException;

import java.util.List;

public class ProfileDaoImpl implements ProfileDao {
    @Override
    public Integer addProfile(Integer parentalProfileId, String profile) throws ServerException {
        return DataBase.getInstance().addProfile(parentalProfileId,profile);
    }
}
