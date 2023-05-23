package net.thumbtack.school.competition.models;

import net.thumbtack.school.competition.dto.request.ExpertDtoRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Expert extends User{

    private List<Integer> profiles;

    public Expert(String name, ArrayList<Integer> profiles, String login, String password) {
        super(name, login, password);
        this.profiles = profiles;
    }


    public List<Integer> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Integer> profiles) {
        this.profiles = profiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Expert expert = (Expert) o;
        return profiles.equals(expert.profiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), profiles);
    }
}
