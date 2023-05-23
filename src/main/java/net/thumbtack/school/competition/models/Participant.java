package net.thumbtack.school.competition.models;

import net.thumbtack.school.competition.dto.request.ParticipantDtoRequest;

import java.util.Objects;

public class Participant extends User{

    private String companyName;

    public Participant(String headName, String companyName, String login, String password) {
        super(headName, login, password);
        this.companyName = companyName;

    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Participant that = (Participant) o;
        return Objects.equals(companyName, that.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName);
    }
}
