package mjc.example.healthplanner;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class BMIClass{

    private static final Map<String, Map<Integer, Map<Integer, Double>>> bmiPercentileTable = GrowthChartData.getGrowthChartData();

    public static String getBMIClass(float bmi, int age,String gender) {
        Log.d("age", "getBMIClass: "+age);
        // 1. 만 65세 이상 노인 기준 적용
        if (age >= 65) {
            if (bmi >= 30.0) return "비만";
            else if (bmi >= 25.0) return "과체중";
            else if (bmi >= 20.1) return "정상";
            else return "저체중";
        }
        // 2. 만 20세 미만 아동 청소년 기준 적용
        else if (age < 20) {
            int percentile = estimatePercentile(bmi, age, gender);
            if (percentile == 1) return "데이터없음";
            else if (percentile >= 95) return "비만";
            else if (percentile >= 85) return "과체중";
            else if (percentile >= 5) return "정상";
            else return "저체중";
        }
        // 3. 만 20-64세 일반 성인 기준 적용
        else {
            if (bmi >= 25.0) return "비만";
            else if (bmi >= 23.0) return "과체중";
            else if (bmi >= 18.5) return "정상";
            else return "저체중";
        }
    }
    //BMI와 성장도표를 이용한 체중 평가퍼센티지를 반환
    private static int estimatePercentile(float bmi, int age, String gender) {
        Map<Integer, Map<Integer, Double>> ageMap = bmiPercentileTable.get(gender);
        if (ageMap == null || !ageMap.containsKey(age)) return -1;

        Map<Integer, Double> percentileMap = ageMap.get(age);
        int closest = -1;
        double minDiff = Double.MAX_VALUE;

        for (Map.Entry<Integer, Double> entry : percentileMap.entrySet()) {
            double diff = Math.abs(entry.getValue() - bmi);
            if (diff < minDiff) {
                minDiff = diff;
                closest = entry.getKey();
            }
        }
        return closest;
    }

}
