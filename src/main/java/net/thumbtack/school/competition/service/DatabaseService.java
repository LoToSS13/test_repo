package net.thumbtack.school.competition.service;

import net.thumbtack.school.competition.dao.DatabaseDao;
import net.thumbtack.school.competition.daoimpl.DatabaseDaoImpl;

public class DatabaseService {

    private DatabaseDao databaseDao = new DatabaseDaoImpl();

    public void clearDatabase(){
        databaseDao.clearDatabase();
    }
}
