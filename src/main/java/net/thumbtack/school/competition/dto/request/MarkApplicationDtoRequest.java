package net.thumbtack.school.competition.dto.request;

import java.util.Objects;

public class MarkApplicationDtoRequest {
    private ApplicationDtoRequest markedApplication;
    private int mark;

    public MarkApplicationDtoRequest(ApplicationDtoRequest markedApplication, int mark) {
        this.markedApplication = markedApplication;
        this.mark = mark;
    }

    public ApplicationDtoRequest getMarkedApplication() {
        return markedApplication;
    }

    public void setMarkedApplication(ApplicationDtoRequest markedApplication) {
        this.markedApplication = markedApplication;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkApplicationDtoRequest that = (MarkApplicationDtoRequest) o;
        return mark == that.mark && Objects.equals(markedApplication, that.markedApplication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(markedApplication, mark);
    }
}
