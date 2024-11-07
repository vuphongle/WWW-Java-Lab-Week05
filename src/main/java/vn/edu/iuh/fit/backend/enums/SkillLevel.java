package vn.edu.iuh.fit.backend.enums;

public enum SkillLevel {
    BASIC(0),
    INTERMEDIATE(1),
    ADVANCED(2);

    private final int level;

    SkillLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
