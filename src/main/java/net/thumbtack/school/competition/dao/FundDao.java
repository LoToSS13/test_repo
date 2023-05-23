package net.thumbtack.school.competition.dao;



import net.thumbtack.school.competition.models.Application;
import net.thumbtack.school.competition.models.Fund;

import java.util.List;

public interface FundDao {
    List<Application> summarizing(Fund fund);
}
