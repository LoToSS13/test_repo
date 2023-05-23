package net.thumbtack.school.competition.dto.response;

import java.util.Objects;

public class IdDtoResponse {
    Integer id;

    public IdDtoResponse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdDtoResponse response = (IdDtoResponse) o;
        return Objects.equals(id, response.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
