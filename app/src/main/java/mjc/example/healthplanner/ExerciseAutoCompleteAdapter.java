package mjc.example.healthplanner; // 실제 패키지 이름으로 변경하세요.

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAutoCompleteAdapter extends ArrayAdapter<Exercise> {

    private List<Exercise> allExercises;
    private List<Exercise> suggestions;

    public ExerciseAutoCompleteAdapter(Context context, List<Exercise> exercises) {
        super(context, 0, exercises);
        this.allExercises = new ArrayList<>(exercises);
        this.suggestions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Exercise getItem(int position) {
        return suggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getKoreanName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    private Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Exercise) resultValue).getKoreanName();
        }

        // 검색된 exercise를 받아서 자동완성 리스트에 추가
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            suggestions.clear();

            if (constraint != null && constraint.length() > 0) {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Exercise exercise : allExercises) {
                    if (exercise.getKoreanName().toLowerCase().contains(filterPattern) ||
                            exercise.getEnglishName().toLowerCase().contains(filterPattern) ||
                            exercise.getCategory().toLowerCase().contains(filterPattern)) {
                        suggestions.add(exercise);
                    }
                }
            }

            filterResults.values = suggestions;
            filterResults.count = suggestions.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };
}