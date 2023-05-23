package net.thumbtack.school.competition.dto.request;

import java.util.List;
import java.util.Objects;

public class ExpertDtoRequest {
    private String name;
    private List<Integer> profiles;
    private String login;
    private String password;

    public ExpertDtoRequest(String name, List<Integer> profiles, String login, String password) {
        this.name = name;
        this.profiles = profiles;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Integer> profiles) {
        this.profiles = profiles;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpertDtoRequest that = (ExpertDtoRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(profiles, that.profiles) && Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, profiles, login, password);
    }
}
