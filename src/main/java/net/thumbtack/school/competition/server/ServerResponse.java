package net.thumbtack.school.competition.server;

import com.google.gson.Gson;
import net.thumbtack.school.competition.dto.response.ErrorDtoResponse;
import net.thumbtack.school.competition.exception.ServerException;

import java.util.Objects;

public class ServerResponse {
    private int responseCode;
    private String responseData;
    private final static Gson gson = new Gson();
    public ServerResponse(int responseCode, String responseData) {
        this.responseCode = responseCode;
        this.responseData = responseData;
    }

    public ServerResponse(String responseData){
        this(ResponseCode.SUCCESS.getCode(), responseData);
    }

    public ServerResponse(ServerException e){
        setResponseCode(ResponseCode.ERROR.getCode());
        setResponseData(gson.toJson(new ErrorDtoResponse(e)));
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerResponse that = (ServerResponse) o;
        return responseCode == that.responseCode && responseData.equals(that.responseData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, responseData);
    }
}
