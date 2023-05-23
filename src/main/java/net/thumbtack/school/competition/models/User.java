package net.thumbtack.school.competition.models;

import net.thumbtack.school.competition.dto.request.LoginDtoRequest;

import java.util.Objects;


public abstract class User {
    private int id;
    private String name;
    private String login;
    private String password;

    public User(String name, String login, String password){
        setName(name);
        setLogin(login);
        setPassword(password);
        setId(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && login.equals(user.login) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, login, password);
    }
}
