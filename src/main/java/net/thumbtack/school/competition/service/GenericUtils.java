package net.thumbtack.school.competition.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.competition.exception.ErrorCode;
import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.models.User;

public class GenericUtils {
    private final static Gson gson = new Gson();

    public static <T> T getClassFromJson(String jsonRequest, Class<T> classType) throws ServerException {
        try{
            return gson.fromJson(jsonRequest, classType);
        } catch (JsonSyntaxException e) {
            System.out.println(jsonRequest);
            throw new ServerException(ErrorCode.WRONG_JSON);
        }
    }

}
