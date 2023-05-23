package net.thumbtack.school.competition.service;

import com.google.gson.Gson;
import net.thumbtack.school.competition.dao.FundDao;
import net.thumbtack.school.competition.daoimpl.FundDaoImpl;
import net.thumbtack.school.competition.dto.request.ApplicationDtoRequest;
import net.thumbtack.school.competition.dto.request.FundDtoRequest;
import net.thumbtack.school.competition.dto.response.FundDtoResponse;
import net.thumbtack.school.competition.exception.ErrorCode;
import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.mapper.ApplicationMapper;
import net.thumbtack.school.competition.mapper.FundMapper;
import net.thumbtack.school.competition.models.Application;
import net.thumbtack.school.competition.models.Fund;
import net.thumbtack.school.competition.server.ServerResponse;

import java.util.ArrayList;
import java.util.List;


public class FundService {
    private final static Gson gson = new Gson();
    private static final int ERROR_CODE = 400;
    private static final int SUCCES_CODE = 200;
    private FundDao fundDao = new FundDaoImpl();

    public ServerResponse summarizing(String json){
        try{
            FundDtoRequest fundDto = GenericUtils.getClassFromJson(json, FundDtoRequest.class);
            Double criticalMark = fundDto.getCriticalMark();
            
            if (criticalMark.compareTo(0.0)!=1||criticalMark.compareTo(5.0)!=-1){
                throw new ServerException(ErrorCode.WRONG_CRITICAL_MARK);
            }
            if (fundDto.getAmount()<=0){
                throw new ServerException(ErrorCode.WRONG_FUND_SUM);
            }
            
            Fund fund = FundMapper.MAPPER.toFund(fundDto);
            List<Application> summarizingApplications = fundDao.summarizing(fund);


            List<ApplicationDtoRequest> applicationsRating = new ArrayList<>();
            for (Application tempApplication: summarizingApplications){

                applicationsRating.add(ApplicationMapper.fromApplication(tempApplication));
            }
            
            FundDtoResponse fundDtoResponse = new FundDtoResponse(applicationsRating);
            return new ServerResponse(gson.toJson(fundDtoResponse));
        } catch (ServerException e){

            return new ServerResponse(e);
        }
    }
}
