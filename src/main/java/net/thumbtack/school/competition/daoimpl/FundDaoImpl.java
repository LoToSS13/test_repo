package net.thumbtack.school.competition.daoimpl;

import net.thumbtack.school.competition.dao.FundDao;
import net.thumbtack.school.competition.database.DataBase;
import net.thumbtack.school.competition.models.Application;
import net.thumbtack.school.competition.models.Fund;

import java.util.List;

public class FundDaoImpl implements FundDao {
    @Override
    public List<Application> summarizing(Fund fund) {
        return DataBase.getInstance().sortApplications(fund);
    }
}
