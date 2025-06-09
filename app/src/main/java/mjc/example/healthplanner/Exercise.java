package mjc.example.healthplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private String englishName;
    private String koreanName;
    private String category;
    private int imageResId;
    private String exerciseId;

    // 기존 생성자는 그대로 둡니다.
    public Exercise(String englishName, String koreanName, String category, int imageResId, String exerciseId) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.category = category;
        this.imageResId = imageResId;
        this.exerciseId = exerciseId;
    }

    // --- Parcelable 구현 시작 ---

    // 1. Parcel에서 데이터를 읽어와 Exercise 객체를 생성하는 생성자입니다.
    // 데이터를 쓴 순서대로 다시 읽어와야 합니다.
    protected Exercise(Parcel in) {
        englishName = in.readString();
        koreanName = in.readString();
        category = in.readString();
        imageResId = in.readInt();
        exerciseId = in.readString();
    }

    // 2. Parcelable 객체를 생성하는 CREATOR 객체입니다.
    // 이 CREATOR는 Parcel에서 Exercise 객체를 만들거나, Exercise 객체 배열을 만들 때 사용됩니다.
    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            // 위에서 정의한 Parcel을 읽는 생성자를 호출합니다.
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            // Exercise 객체의 배열을 생성합니다.
            return new Exercise[size];
        }
    };

    // 3. 객체의 내용을 설명하는 메서드입니다.
    // 특별한 파일 디스크립터를 포함하지 않으므로 보통 0을 반환합니다.
    @Override
    public int describeContents() {
        return 0;
    }

    // 4. Exercise 객체의 데이터를 Parcel에 쓰는 메서드입니다.
    // 이 메서드에 데이터를 쓰는 순서가 위에서 읽는 순서와 정확히 일치해야 합니다.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(englishName);
        dest.writeString(koreanName);
        dest.writeString(category);
        dest.writeInt(imageResId);
        dest.writeString(exerciseId);
    }

    // --- Parcelable 구현 끝 ---

    // 기존 Getter 메서드들은 그대로 둡니다.
    public String getEnglishName() {
        return englishName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public String getCategory() {
        return category;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getExerciseId(){
        return exerciseId;
    }

    @Override
    public String toString() {
        return koreanName; // 자동 완성 드롭다운에 한국어 이름이 표시되도록 함
    }
}