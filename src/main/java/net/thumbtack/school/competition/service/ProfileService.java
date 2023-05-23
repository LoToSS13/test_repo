package net.thumbtack.school.competition.service;

import com.google.gson.Gson;
import net.thumbtack.school.competition.dao.ProfileDao;
import net.thumbtack.school.competition.daoimpl.ProfileDaoImpl;
import net.thumbtack.school.competition.dto.request.AddProfileDtoRequest;
import net.thumbtack.school.competition.dto.response.IdDtoResponse;
import net.thumbtack.school.competition.exception.ErrorCode;
import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.mapper.ProfileMapper;
import net.thumbtack.school.competition.server.ServerResponse;


public class ProfileService {
    private final static Gson gson = new Gson();

    private final ProfileDao profileDao = new ProfileDaoImpl();


    public ServerResponse addProfiles(String requestJsonString) {
        try {
            AddProfileDtoRequest addProfileDtoRequest = GenericUtils.getClassFromJson(requestJsonString, AddProfileDtoRequest.class);

            validateProfile(addProfileDtoRequest);

            Integer id = profileDao.addProfile(addProfileDtoRequest.getParentProfileId(), addProfileDtoRequest.getExplanation());

            IdDtoResponse response = ProfileMapper.fromProfileIds(id);

            return new ServerResponse(gson.toJson(response));
        } catch (ServerException e){

            return new ServerResponse(e);
        }
    }


    private void validateProfile(AddProfileDtoRequest addProfileDtoRequest) throws ServerException {
        if (addProfileDtoRequest == null || addProfileDtoRequest.getExplanation() == null){
            throw new ServerException(ErrorCode.WRONG_JSON);
        }
    }



}
