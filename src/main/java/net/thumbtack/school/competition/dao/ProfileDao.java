package net.thumbtack.school.competition.dao;

import net.thumbtack.school.competition.exception.ServerException;

import java.util.List;

public interface ProfileDao {

    Integer addProfile(Integer parentalProfileId, String profile) throws ServerException;
}
