package net.thumbtack.school.competition.dto.request;

import net.thumbtack.school.competition.models.Participant;

import java.util.Objects;

public class ParticipantDtoRequest {
    private final String headName;
    private final String companyName;
    private final String login;
    private final String password;

    @Default
    public ParticipantDtoRequest(String headName, String companyName, String login, String password) {
        this.headName = headName;
        this.companyName = companyName;
        this.login = login;
        this.password = password;
    }

    public ParticipantDtoRequest(Participant participant){
        this.headName = participant.getName();
        this.companyName = participant.getCompanyName();
        this.login = participant.getLogin();
        this.password = participant.getPassword();
    }

    public String getHeadName() {
        return headName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantDtoRequest that = (ParticipantDtoRequest) o;
        return headName.equals(that.headName) && companyName.equals(that.companyName) && login.equals(that.login) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headName, companyName, login, password);
    }

    @Override
    public String toString() {
        return "ParticipantDtoRequest{" +
                "headName='" + headName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
