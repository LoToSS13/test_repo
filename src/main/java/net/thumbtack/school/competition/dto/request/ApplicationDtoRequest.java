package net.thumbtack.school.competition.dto.request;

import java.util.List;
import java.util.Objects;

public class ApplicationDtoRequest {
    private String name;
    private String description;
    private List<Integer> profiles;
    private int amount;

    public ApplicationDtoRequest(String name, String description, List<Integer> profiles, int amount) {
        this.name = name;
        this.description = description;
        this.profiles = profiles;
        this.amount = amount;
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

    public void setProfiles(List<Integer> profiles) {
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
        ApplicationDtoRequest that = (ApplicationDtoRequest) o;
        return amount == that.amount && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(profiles, that.profiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, profiles, amount);
    }

    @Override
    public String toString() {
        return "ApplicationDtoRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", profiles=" + profiles +
                ", amount=" + amount +
                '}';
    }
}