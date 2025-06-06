package mjc.example.healthplanner;

import java.util.HashMap;
import java.util.Map;

public class GrowthChartData {

    public static Map<String, Map<Integer, Map<Integer, Double>>> getGrowthChartData() {
        Map<String, Map<Integer, Map<Integer, Double>>> data = new HashMap<>();

        // Data for male
        Map<Integer, Map<Integer, Double>> maleAgeMap = new HashMap<>();
        Map<Integer, Double> male2 = new HashMap<>();
        male2.put(3, 14.4);
        male2.put(5, 14.8);
        male2.put(10, 15.3);
        male2.put(25, 16.1);
        male2.put(50, 17.0);
        male2.put(75, 17.8);
        male2.put(85, 18.2);
        male2.put(95, 18.9);
        male2.put(97, 19.1);
        maleAgeMap.put(2, male2);

        Map<Integer, Double> male3 = new HashMap<>();
        male3.put(3, 14.1);
        male3.put(5, 14.4);
        male3.put(10, 14.8);
        male3.put(25, 15.6);
        male3.put(50, 16.4);
        male3.put(75, 17.2);
        male3.put(85, 17.6);
        male3.put(95, 18.4);
        male3.put(97, 18.7);
        maleAgeMap.put(3, male3);

        Map<Integer, Double> male4 = new HashMap<>();
        male4.put(3, 13.8);
        male4.put(5, 14.1);
        male4.put(10, 14.5);
        male4.put(25, 15.2);
        male4.put(50, 16.0);
        male4.put(75, 16.8);
        male4.put(85, 17.3);
        male4.put(95, 18.2);
        male4.put(97, 18.5);
        maleAgeMap.put(4, male4);

        Map<Integer, Double> male5 = new HashMap<>();
        male5.put(3, 13.7);
        male5.put(5, 13.9);
        male5.put(10, 14.3);
        male5.put(25, 15.0);
        male5.put(50, 15.8);
        male5.put(75, 16.7);
        male5.put(85, 17.3);
        male5.put(95, 18.4);
        male5.put(97, 18.8);
        maleAgeMap.put(5, male5);

        Map<Integer, Double> male6 = new HashMap<>();
        male6.put(3, 13.6);
        male6.put(5, 13.8);
        male6.put(10, 14.2);
        male6.put(25, 14.9);
        male6.put(50, 15.8);
        male6.put(75, 16.9);
        male6.put(85, 17.6);
        male6.put(95, 18.9);
        male6.put(97, 19.4);
        maleAgeMap.put(6, male6);

        Map<Integer, Double> male7 = new HashMap<>();
        male7.put(3, 13.6);
        male7.put(5, 13.8);
        male7.put(10, 14.2);
        male7.put(25, 15.0);
        male7.put(50, 16.0);
        male7.put(75, 17.3);
        male7.put(85, 18.1);
        male7.put(95, 19.7);
        male7.put(97, 20.3);
        maleAgeMap.put(7, male7);

        Map<Integer, Double> male8 = new HashMap<>();
        male8.put(3, 13.7);
        male8.put(5, 13.9);
        male8.put(10, 14.3);
        male8.put(25, 15.2);
        male8.put(50, 16.3);
        male8.put(75, 17.8);
        male8.put(85, 18.8);
        male8.put(95, 20.6);
        male8.put(97, 21.4);
        maleAgeMap.put(8, male8);

        Map<Integer, Double> male9 = new HashMap<>();
        male9.put(3, 13.8);
        male9.put(5, 14.1);
        male9.put(10, 14.5);
        male9.put(25, 15.5);
        male9.put(50, 16.7);
        male9.put(75, 18.4);
        male9.put(85, 19.6);
        male9.put(95, 21.7);
        male9.put(97, 22.6);
        maleAgeMap.put(9, male9);

        Map<Integer, Double> male10 = new HashMap<>();
        male10.put(3, 14.0);
        male10.put(5, 14.3);
        male10.put(10, 14.8);
        male10.put(25, 15.8);
        male10.put(50, 17.2);
        male10.put(75, 19.1);
        male10.put(85, 20.3);
        male10.put(95, 22.8);
        male10.put(97, 23.8);
        maleAgeMap.put(10, male10);

        Map<Integer, Double> male11 = new HashMap<>();
        male11.put(3, 14.3);
        male11.put(5, 14.6);
        male11.put(10, 15.1);
        male11.put(25, 16.3);
        male11.put(50, 17.8);
        male11.put(75, 19.8);
        male11.put(85, 21.1);
        male11.put(95, 23.9);
        male11.put(97, 25.0);
        maleAgeMap.put(11, male11);

        Map<Integer, Double> male12 = new HashMap<>();
        male12.put(3, 14.6);
        male12.put(5, 14.9);
        male12.put(10, 15.5);
        male12.put(25, 16.8);
        male12.put(50, 18.4);
        male12.put(75, 20.6);
        male12.put(85, 21.9);
        male12.put(95, 24.8);
        male12.put(97, 26.0);
        maleAgeMap.put(12, male12);

        Map<Integer, Double> male13 = new HashMap<>();
        male13.put(3, 15.0);
        male13.put(5, 15.3);
        male13.put(10, 16.0);
        male13.put(25, 17.4);
        male13.put(50, 19.1);
        male13.put(75, 21.4);
        male13.put(85, 22.7);
        male13.put(95, 25.6);
        male13.put(97, 26.8);
        maleAgeMap.put(13, male13);

        Map<Integer, Double> male14 = new HashMap<>();
        male14.put(3, 15.4);
        male14.put(5, 15.8);
        male14.put(10, 16.5);
        male14.put(25, 18.0);
        male14.put(50, 19.8);
        male14.put(75, 22.1);
        male14.put(85, 23.5);
        male14.put(95, 26.3);
        male14.put(97, 27.5);
        maleAgeMap.put(14, male14);

        Map<Integer, Double> male15 = new HashMap<>();
        male15.put(3, 15.9);
        male15.put(5, 16.3);
        male15.put(10, 17.0);
        male15.put(25, 18.6);
        male15.put(50, 20.5);
        male15.put(75, 22.8);
        male15.put(85, 24.2);
        male15.put(95, 26.9);
        male15.put(97, 28.1);
        maleAgeMap.put(15, male15);

        Map<Integer, Double> male16 = new HashMap<>();
        male16.put(3, 16.4);
        male16.put(5, 16.8);
        male16.put(10, 17.5);
        male16.put(25, 19.1);
        male16.put(50, 21.1);
        male16.put(75, 23.4);
        male16.put(85, 24.8);
        male16.put(95, 27.4);
        male16.put(97, 28.6);
        maleAgeMap.put(16, male16);

        Map<Integer, Double> male17 = new HashMap<>();
        male17.put(3, 16.8);
        male17.put(5, 17.2);
        male17.put(10, 18.0);
        male17.put(25, 19.7);
        male17.put(50, 21.6);
        male17.put(75, 24.0);
        male17.put(85, 25.4);
        male17.put(95, 27.8);
        male17.put(97, 29.1);
        maleAgeMap.put(17, male17);

        Map<Integer, Double> male18 = new HashMap<>();
        male18.put(3, 17.2);
        male18.put(5, 17.6);
        male18.put(10, 18.4);
        male18.put(25, 20.2);
        male18.put(50, 22.1);
        male18.put(75, 24.5);
        male18.put(85, 25.9);
        male18.put(95, 28.3);
        male18.put(97, 29.5);
        maleAgeMap.put(18, male18);

        Map<Integer, Double> male19 = new HashMap<>();
        male19.put(3, 17.5);
        male19.put(5, 17.9);
        male19.put(10, 18.7);
        male19.put(25, 20.5);
        male19.put(50, 22.5);
        male19.put(75, 25.0);
        male19.put(85, 26.4);
        male19.put(95, 28.7);
        male19.put(97, 29.9);
        maleAgeMap.put(19, male19);

        data.put("male", maleAgeMap);

        // Data for female
        Map<Integer, Map<Integer, Double>> femaleAgeMap = new HashMap<>();
        Map<Integer, Double> female2 = new HashMap<>();
        female2.put(3, 14.0);
        female2.put(5, 14.3);
        female2.put(10, 14.8);
        female2.put(25, 15.6);
        female2.put(50, 16.5);
        female2.put(75, 17.3);
        female2.put(85, 17.8);
        female2.put(95, 18.4);
        female2.put(97, 18.7);
        femaleAgeMap.put(2, female2);

        Map<Integer, Double> female3 = new HashMap<>();
        female3.put(3, 13.7);
        female3.put(5, 14.0);
        female3.put(10, 14.4);
        female3.put(25, 15.2);
        female3.put(50, 16.1);
        female3.put(75, 16.9);
        female3.put(85, 17.4);
        female3.put(95, 18.2);
        female3.put(97, 18.5);
        femaleAgeMap.put(3, female3);

        Map<Integer, Double> female4 = new HashMap<>();
        female4.put(3, 13.5);
        female4.put(5, 13.8);
        female4.put(10, 14.2);
        female4.put(25, 14.9);
        female4.put(50, 15.8);
        female4.put(75, 16.8);
        female4.put(85, 17.4);
        female4.put(95, 18.4);
        female4.put(97, 18.8);
        femaleAgeMap.put(4, female4);

        Map<Integer, Double> female5 = new HashMap<>();
        female5.put(3, 13.4);
        female5.put(5, 13.7);
        female5.put(10, 14.1);
        female5.put(25, 14.8);
        female5.put(50, 15.7);
        female5.put(75, 16.8);
        female5.put(85, 17.5);
        female5.put(95, 18.8);
        female5.put(97, 19.3);
        femaleAgeMap.put(5, female5);

        Map<Integer, Double> female6 = new HashMap<>();
        female6.put(3, 13.4);
        female6.put(5, 13.6);
        female6.put(10, 14.0);
        female6.put(25, 14.8);
        female6.put(50, 15.8);
        female6.put(75, 17.0);
        female6.put(85, 17.8);
        female6.put(95, 19.4);
        female6.put(97, 20.0);
        femaleAgeMap.put(6, female6);

        Map<Integer, Double> female7 = new HashMap<>();
        female7.put(3, 13.4);
        female7.put(5, 13.7);
        female7.put(10, 14.1);
        female7.put(25, 15.0);
        female7.put(50, 16.0);
        female7.put(75, 17.4);
        female7.put(85, 18.4);
        female7.put(95, 20.2);
        female7.put(97, 21.0);
        femaleAgeMap.put(7, female7);

        Map<Integer, Double> female8 = new HashMap<>();
        female8.put(3, 13.5);
        female8.put(5, 13.8);
        female8.put(10, 14.3);
        female8.put(25, 15.2);
        female8.put(50, 16.4);
        female8.put(75, 18.0);
        female8.put(85, 19.1);
        female8.put(95, 21.2);
        female8.put(97, 22.1);
        femaleAgeMap.put(8, female8);

        Map<Integer, Double> female9 = new HashMap<>();
        female9.put(3, 13.7);
        female9.put(5, 14.0);
        female9.put(10, 14.5);
        female9.put(25, 15.6);
        female9.put(50, 16.9);
        female9.put(75, 18.7);
        female9.put(85, 19.9);
        female9.put(95, 22.3);
        female9.put(97, 23.4);
        femaleAgeMap.put(9, female9);

        Map<Integer, Double> female10 = new HashMap<>();
        female10.put(3, 14.0);
        female10.put(5, 14.3);
        female10.put(10, 14.9);
        female10.put(25, 16.1);
        female10.put(50, 17.5);
        female10.put(75, 19.5);
        female10.put(85, 20.8);
        female10.put(95, 23.4);
        female10.put(97, 24.5);
        femaleAgeMap.put(10, female10);

        Map<Integer, Double> female11 = new HashMap<>();
        female11.put(3, 14.4);
        female11.put(5, 14.7);
        female11.put(10, 15.4);
        female11.put(25, 16.6);
        female11.put(50, 18.1);
        female11.put(75, 20.3);
        female11.put(85, 21.6);
        female11.put(95, 24.3);
        female11.put(97, 25.5);
        femaleAgeMap.put(11, female11);

        Map<Integer, Double> female12 = new HashMap<>();
        female12.put(3, 14.8);
        female12.put(5, 15.2);
        female12.put(10, 15.9);
        female12.put(25, 17.2);
        female12.put(50, 18.8);
        female12.put(75, 21.0);
        female12.put(85, 22.4);
        female12.put(95, 25.2);
        female12.put(97, 26.4);
        femaleAgeMap.put(12, female12);

        Map<Integer, Double> female13 = new HashMap<>();
        female13.put(3, 15.3);
        female13.put(5, 15.6);
        female13.put(10, 16.4);
        female13.put(25, 17.8);
        female13.put(50, 19.4);
        female13.put(75, 21.7);
        female13.put(85, 23.1);
        female13.put(95, 25.9);
        female13.put(97, 27.2);
        femaleAgeMap.put(13, female13);

        Map<Integer, Double> female14 = new HashMap<>();
        female14.put(3, 15.7);
        female14.put(5, 16.1);
        female14.put(10, 16.9);
        female14.put(25, 18.3);
        female14.put(50, 20.0);
        female14.put(75, 22.3);
        female14.put(85, 23.7);
        female14.put(95, 26.5);
        female14.put(97, 27.8);
        femaleAgeMap.put(14, female14);

        Map<Integer, Double> female15 = new HashMap<>();
        female15.put(3, 16.1);
        female15.put(5, 16.5);
        female15.put(10, 17.3);
        female15.put(25, 18.7);
        female15.put(50, 20.5);
        female15.put(75, 22.8);
        female15.put(85, 24.2);
        female15.put(95, 27.0);
        female15.put(97, 28.3);
        femaleAgeMap.put(15, female15);

        Map<Integer, Double> female16 = new HashMap<>();
        female16.put(3, 16.4);
        female16.put(5, 16.8);
        female16.put(10, 17.6);
        female16.put(25, 19.1);
        female16.put(50, 20.9);
        female16.put(75, 23.2);
        female16.put(85, 24.6);
        female16.put(95, 27.4);
        female16.put(97, 28.8);
        femaleAgeMap.put(16, female16);

        Map<Integer, Double> female17 = new HashMap<>();
        female17.put(3, 16.6);
        female17.put(5, 17.0);
        female17.put(10, 17.8);
        female17.put(25, 19.3);
        female17.put(50, 21.2);
        female17.put(75, 23.5);
        female17.put(85, 25.0);
        female17.put(95, 27.8);
        female17.put(97, 29.1);
        femaleAgeMap.put(17, female17);

        Map<Integer, Double> female18 = new HashMap<>();
        female18.put(3, 16.7);
        female18.put(5, 17.2);
        female18.put(10, 18.0);
        female18.put(25, 19.5);
        female18.put(50, 21.4);
        female18.put(75, 23.8);
        female18.put(85, 25.3);
        female18.put(95, 28.1);
        female18.put(97, 29.4);
        femaleAgeMap.put(18, female18);

        Map<Integer, Double> female19 = new HashMap<>();
        female19.put(3, 16.8);
        female19.put(5, 17.3);
        female19.put(10, 18.1);
        female19.put(25, 19.7);
        female19.put(50, 21.6);
        female19.put(75, 24.0);
        female19.put(85, 25.5);
        female19.put(95, 28.4);
        female19.put(97, 29.7);
        femaleAgeMap.put(19, female19);

        data.put("female", femaleAgeMap);

        return data;
    }
}