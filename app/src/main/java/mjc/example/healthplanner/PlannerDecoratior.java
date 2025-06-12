package mjc.example.healthplanner;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;


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
import java.util.Collection;
import java.util.HashSet;
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



    //  일요일 데코레이터 (빨간색)
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

    //  토요일 데코레이터 (파란색)
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

    //기록이 있는 날 꾸미기
    public static class GrayDateDecorator implements DayViewDecorator {

        private final HashSet<CalendarDay> dates;
        private final Drawable grayBackground;

        public GrayDateDecorator(Context context, Collection<CalendarDay> dates) {
            this.dates = new HashSet<>(dates);
            this.grayBackground = ContextCompat.getDrawable(context, R.drawable.selected_background_calendar); // 회색
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(grayBackground);
        }
    }
    // 오늘 날짜를 강조
    public static class TodayDecorator implements DayViewDecorator {

        private final CalendarDay today;

        public TodayDecorator() {
            today = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(today);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan( Color.parseColor("#000000")));
            view.addSpan(new StyleSpan(Typeface.BOLD)); // 텍스트를 볼드체로
            view.addSpan(new RelativeSizeSpan(1.2f)); // 텍스트 크기를 약간 키움
            // view.setBackgroundDrawable(ContextCompat.getDrawable(view.view.getContext(), R.drawable.your_today_background));
        }
    }
    // 오늘 날을 제외하고 흐릿하게
    public static class DimDatesDecorator implements DayViewDecorator {

        private final CalendarDay today;
        private final int dimColor;

        public DimDatesDecorator() {
            today = CalendarDay.today();
            dimColor = Color.parseColor("#CCCCCC"); // 흐릿하게 만들 색상 (회색 계열)
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            // 오늘 날짜가 아닌 모든 날짜를 흐릿하게 처리
            return !day.equals(today);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(dimColor)); // 글자 색상을 흐릿한 색으로 변경

        }
    }
    // 선택된 날짜 디자인
    public static class SelectedDateDecorator implements DayViewDecorator {

        private final CalendarDay selectedDate;
        private Drawable defaultSelectionDrawable;

        public SelectedDateDecorator(CalendarDay date,Context context) {
            this.selectedDate = date;
            this.defaultSelectionDrawable = ContextCompat.getDrawable(context, R.drawable.selected_calendar);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(selectedDate);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan( Color.parseColor("#000000")));
            view.addSpan(new StyleSpan(Typeface.BOLD)); // 볼드
            view.addSpan(new RelativeSizeSpan(1.2f)); // 크기 키움

            if (defaultSelectionDrawable != null) {
                view.setSelectionDrawable(defaultSelectionDrawable);
        }
    }




}}
