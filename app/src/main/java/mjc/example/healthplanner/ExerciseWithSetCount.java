package mjc.example.healthplanner;

public class ExerciseWithSetCount {
    private Exercise exercise;
    private String setCount;

    public ExerciseWithSetCount(Exercise exercise, String setCount) {
        this.exercise = exercise;
        this.setCount = setCount;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public String getSetCount() {
        return setCount;
    }

    public void setSetCount(String setCount) {
        this.setCount = setCount;
    }
}
