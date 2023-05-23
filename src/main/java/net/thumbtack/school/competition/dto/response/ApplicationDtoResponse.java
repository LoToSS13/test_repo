package net.thumbtack.school.competition.dto.response;

import java.util.Objects;

public class ApplicationDtoResponse {
    String result;

    public ApplicationDtoResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationDtoResponse that = (ApplicationDtoResponse) o;
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result);
    }
}
