// mjc.example.healthplanner/Record.java
package mjc.example.healthplanner;

public class Record {
    private String recordId;
    private String exerciseId; // 기존의 exerciseId는 유지 (DB 연동 시 필요할 수 있음)
    private String setCount;

    // 생성자 (Exercise 객체를 인자로 받도록 변경)
    public Record(String recordId, String exerciseId, String setCount) {
        this.recordId = recordId;
        this.exerciseId = exerciseId;
        this.setCount = setCount;
    }

    // Getters
    public String getRecordId() {
        return recordId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public String getSetCount() {
        return setCount;
    }

    // 세터 (setCount는 변경될 수 있으므로 세터 추가)
    public void setSetCount(String setCount) {
        this.setCount = setCount;
    }

}