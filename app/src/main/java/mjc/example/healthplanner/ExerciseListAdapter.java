package mjc.example.healthplanner; //

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder> {

    private List<Exercise> exercises;

    // 아이템 클릭 리스너 인터페이스
    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ExerciseListAdapter() {
        this.exercises = new ArrayList<>();
    }

    public void submitList(List<Exercise> newExercises) {
        this.exercises = newExercises;
        notifyDataSetChanged();
    }

    // 특정 position의 아이템을 가져오는 메서드
    public Exercise getItem(int position) {
        if (position >= 0 && position < exercises.size()) {
            return exercises.get(position);
        }
        return null;
    }


    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_container, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.bind(exercise);

        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(exercise);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }



    // RecyclerView.ViewHolder

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        ImageView exerciseImage;
        TextView koreanName;
        TextView category; // 분류 TextView 추가


        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            // item_exercise.xml에 정의된 뷰들을 찾아 멤버 변수에 할당
            exerciseImage = itemView.findViewById(R.id.exercise_img);
            koreanName = itemView.findViewById(R.id.korean_name);
            category = itemView.findViewById(R.id.category); // 분류 TextView 연결
        }

        // Exercise 객체의 데이터를 뷰에 설정하는 메서드
        public void bind(Exercise exercise) {
            exerciseImage.setImageResource(exercise.getImageResId());
            koreanName.setText(exercise.getKoreanName());
            category.setText("분류: " + exercise.getCategory()); // 분류 텍스트 설정
        }
    }
}
