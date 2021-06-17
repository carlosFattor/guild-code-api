package org.guildcode.domain.location;


import lombok.Getter;

@Getter
public enum LocationType {
    POINT("Point");

    private final String description;

    LocationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
