package mjc.example.healthplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private String englishName;
    private String koreanName;
    private String category;
    private int imageResId;
    private String exerciseId;

    public Exercise(String englishName, String koreanName, String category, int imageResId, String exerciseId) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.category = category;
        this.imageResId = imageResId;
        this.exerciseId = exerciseId;
    }

    // --- Parcelable 구현 시작 ---

    //Parcel에서 데이터를 읽어와 Exercise 객체를 생성하는 생성자
    protected Exercise(Parcel in) {
        englishName = in.readString();
        koreanName = in.readString();
        category = in.readString();
        imageResId = in.readInt();
        exerciseId = in.readString();
    }

    // Parcelable 객체를 생성하는 CREATOR 객체
    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    // 객체의 내용을 설명하는 메서드
    @Override
    public int describeContents() {
        return 0;
    }

    // Exercise 객체의 데이터를 Parcel에 쓰는 메서드
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(englishName);
        dest.writeString(koreanName);
        dest.writeString(category);
        dest.writeInt(imageResId);
        dest.writeString(exerciseId);
    }

    // --- Parcelable 구현 끝 ---

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