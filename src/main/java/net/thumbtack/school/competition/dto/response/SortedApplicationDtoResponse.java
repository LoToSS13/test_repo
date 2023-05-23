package net.thumbtack.school.competition.dto.response;

import java.util.Objects;
import java.util.Set;

public class SortedApplicationDtoResponse {
    private Set<Integer> applicationsIDs;

    public SortedApplicationDtoResponse(Set<Integer> applicationsIDs) {
        this.applicationsIDs = applicationsIDs;
    }

    public Set<Integer> getApplicationsIDs() {
        return applicationsIDs;
    }

    public void setApplicationsIDs(Set<Integer> applicationsIDs) {
        this.applicationsIDs = applicationsIDs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortedApplicationDtoResponse that = (SortedApplicationDtoResponse) o;
        return Objects.equals(applicationsIDs, that.applicationsIDs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationsIDs);
    }
}
