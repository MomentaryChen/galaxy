package com.momentary.galaxy.constant;

public enum PlayerLevelEnum {
    HIGH(1, "Professional", "Advanced level"),
    MEDIUM(2, "Intermediate", "Intermediate level"),
    LOW(3, "Amateur", "Beginner level");

    private int levelNumber;
    private String competitionLevel;
    private String description;

    PlayerLevelEnum(int levelNumber, String competitionLevel, String description) {
        this.levelNumber = levelNumber;
        this.competitionLevel = competitionLevel;
        this.description = description;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String getCompetitionLevel() {
        return competitionLevel;
    }

    public String getDescription() {
        return description;
    }

    public static PlayerLevelEnum fromLevelNumber(int levelNumber) {
        for (PlayerLevelEnum level : PlayerLevelEnum.values()) {
            if (level.getLevelNumber() == levelNumber) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid level number: " + levelNumber);
    }
}
