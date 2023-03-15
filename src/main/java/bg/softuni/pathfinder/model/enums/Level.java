package bg.softuni.pathfinder.model.enums;

import java.util.Map;

public enum Level {
    BEGINNER, INTERMEDIATE, ADVANCED;

    public static final Map<String, Level> levelMap = Map.of(
            "BEGINNER", BEGINNER,
            "INTERMEDIATE", INTERMEDIATE,
            "ADVANCED", ADVANCED
    );
}
