package net.thumbtack.school.competition.dto.response;

import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.server.ResponseCode;
import net.thumbtack.school.competition.server.ServerResponse;

import java.util.Objects;

public class ErrorDtoResponse {
    private final String error;

    public ErrorDtoResponse(String error) {
        this.error = error;
    }
    public ErrorDtoResponse(ServerException e){
        this.error = e.getErrorCode().getDescription();
    }
    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDtoResponse that = (ErrorDtoResponse) o;
        return Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }
}
