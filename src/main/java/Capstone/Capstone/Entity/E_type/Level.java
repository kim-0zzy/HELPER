package Capstone.Capstone.Entity.E_type;

public enum Level {
    INTERMEDIATE(180, null),
    NOVICE(90,INTERMEDIATE),
    BEGINNER(30,NOVICE),
    UNTRAINED(0,BEGINNER);

    private int career;
    private Level nextLevel;
    Level(int career, Level nextLevel) {
        this.career = career;
        this.nextLevel = nextLevel;
    }

    public static boolean availableLevelUp(Level level, int career){
        if(level.nextLevel == null){
            return false;
        }
        return level.nextLevel.career <= career;
    }
    public static Level levelUp(Level level){
        return level.nextLevel;
    }
}
