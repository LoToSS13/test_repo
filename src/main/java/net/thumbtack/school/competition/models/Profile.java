package net.thumbtack.school.competition.models;

import java.util.Objects;

public class Profile {
    private int id;
    private String explanation;
    private Profile parent;


    public Profile(int id, String explanation, Profile parent) {
        this.id = id;
        this.explanation = explanation;
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Profile getParent() {
        return parent;
    }

    public void setParent(Profile parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id && Objects.equals(explanation, profile.explanation) && Objects.equals(parent, profile.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, explanation, parent);
    }
}
