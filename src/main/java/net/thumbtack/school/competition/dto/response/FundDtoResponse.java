package net.thumbtack.school.competition.dto.response;

import net.thumbtack.school.competition.dto.request.ApplicationDtoRequest;

import java.util.List;
import java.util.Objects;


public class FundDtoResponse {
    List<ApplicationDtoRequest> applicationsRating;

    public FundDtoResponse(List<ApplicationDtoRequest> applicationsRating) {
        this.applicationsRating = applicationsRating;
    }

    public List<ApplicationDtoRequest> getApplicationRating() {
        return applicationsRating;
    }

    public void setApplicationRating(List<ApplicationDtoRequest> grantsRating) {
        this.applicationsRating = grantsRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FundDtoResponse that = (FundDtoResponse) o;
        return applicationsRating.equals(that.applicationsRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationsRating);
    }
}
