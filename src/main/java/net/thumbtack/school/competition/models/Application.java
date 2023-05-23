package net.thumbtack.school.competition.models;

import net.thumbtack.school.competition.dto.request.ApplicationDtoRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application {
    private int id;
    private Participant participant;
    private String name;
    private String description;
    private List<Integer> profiles;
    private int amount;

    public Application(Participant participant, String name, String description, ArrayList<Integer> profiles, int amount) {
        this.id =  0;
        this.participant = participant;
        this.name = name;
        this.description = description;
        this.profiles = profiles;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Integer> profiles) {
        this.profiles = profiles;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id && amount == that.amount && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(profiles, that.profiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, participant, name, description, profiles, amount);
    }

}
