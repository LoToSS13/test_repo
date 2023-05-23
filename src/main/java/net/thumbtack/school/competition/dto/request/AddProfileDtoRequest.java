package net.thumbtack.school.competition.dto.request;

import java.util.List;
import java.util.Objects;

public class AddProfileDtoRequest {
    int parentProfileId;
    String explanation;

    public AddProfileDtoRequest(int parentProfileId, String explanation) {
        this.parentProfileId = parentProfileId;
        this.explanation = explanation;
    }

    public int getParentProfileId() {
        return parentProfileId;
    }

    public void setParentProfileId(int parentProfileId) {
        this.parentProfileId = parentProfileId;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
