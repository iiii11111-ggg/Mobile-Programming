package mjc.example.healthplanner;


import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;


import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import org.threeten.bp.DayOfWeek;

import java.util.Calendar;
import java.util.Locale;

public class PlannerDecoratior {

    public static void setupCalendar(MaterialCalendarView calendarView, Context context) {

        // 요일을 한국어로 표시
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(
                context.getResources().getStringArray(R.array.custom_weekdays_korean)));

        // 헤더 포맷 설정 (yyyy년 MM월)
        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                int year = day.getYear();
                int month = day.getMonth();
                return String.format(Locale.KOREA, "%d년 %d월", year, month);
            }
        });

        // 일요일 빨간색, 토요일 파란색 데코레이터 추가
        calendarView.addDecorators(
                new SundayDecorator(context),
                new SaturdayDecorator(context)
        );


        //요일 색상 변경
        calendarView.setWeekDayFormatter(new WeekDayFormatter() {
            @Override
            public CharSequence format(DayOfWeek dayOfWeek) {
                String label = ""; // 한글 요일
                int color = Color.BLACK;

                switch (dayOfWeek) {
                    case SUNDAY:
                        label = "일";
                        color = ContextCompat.getColor(context, R.color.red_sunday);
                        break;
                    case MONDAY:
                        label = "월";
                        break;
                    case TUESDAY:
                        label = "화";
                        break;
                    case WEDNESDAY:
                        label = "수";
                        break;
                    case THURSDAY:
                        label = "목";
                        break;
                    case FRIDAY:
                        label = "금";
                        break;
                    case SATURDAY:
                        label = "토";
                        color = ContextCompat.getColor(context, R.color.blue_saturday);
                        break;
                }

                SpannableString spannable = new SpannableString(label);
                spannable.setSpan(new ForegroundColorSpan(color), 0, label.length(), 0);
                return spannable;
            }
        });
    }



    // 🔴 일요일 데코레이터 (빨간색)
    private static class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();
        private final Context context;

        public SundayDecorator(Context context) {
            this.context = context;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            calendar.set(day.getYear(), day.getMonth(), day.getDay());
            return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(
                    ContextCompat.getColor(context, R.color.red_sunday)));
        }
    }

    // 🔵 토요일 데코레이터 (파란색)
    private static class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();
        private final Context context;

        public SaturdayDecorator(Context context) {
            this.context = context;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            calendar.set(day.getYear(), day.getMonth(), day.getDay());
            return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(
                    ContextCompat.getColor(context, R.color.blue_saturday)));
        }
    }
}
