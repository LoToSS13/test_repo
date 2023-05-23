package net.thumbtack.school.competition.dto.request;

import java.util.List;
import java.util.Objects;

public class SortedApplicationDtoRequest {
    private List<Integer> profiles;

    public SortedApplicationDtoRequest(List<Integer> profiles) {
        this.profiles = profiles;
    }

    public List<Integer> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Integer> profiles) {
        this.profiles = profiles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortedApplicationDtoRequest that = (SortedApplicationDtoRequest) o;
        return Objects.equals(profiles, that.profiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profiles);
    }
}
