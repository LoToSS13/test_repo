package net.thumbtack.school.competition.daoimpl;

import net.thumbtack.school.competition.dao.DatabaseDao;
import net.thumbtack.school.competition.database.DataBase;

public class DatabaseDaoImpl implements DatabaseDao {
    @Override
    public void clearDatabase() {
        DataBase.getInstance().Clear();
    }

}
