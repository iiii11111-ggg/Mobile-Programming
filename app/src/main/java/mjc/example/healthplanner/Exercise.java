package mjc.example.healthplanner;

public class Exercise {
    private String englishName;
    private String koreanName;
    private String category;
    private int imageResId; // drawable 리소스 ID (예: R.drawable.overhead_press)

    public Exercise(String englishName, String koreanName, String category, int imageResId) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.category = category;
        this.imageResId = imageResId;
    }

    // Getters
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

    @Override
    public String toString() {
        return koreanName; // 자동 완성 드롭다운에 한국어 이름이 표시되도록 함
    }
}