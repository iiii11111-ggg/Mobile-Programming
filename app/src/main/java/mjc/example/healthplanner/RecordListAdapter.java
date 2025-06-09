package mjc.example.healthplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {

    private List<ExerciseWithSetCount> exerciseList;

    // 아이템 클릭 리스너 (RecyclerView 항목 전체 클릭)
    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public RecordListAdapter() {
        this.exerciseList = new ArrayList<>();
    }

    // 외부에서 새로운 Exercise 리스트를 받아와서 업데이트하는 메서드
    public void submitList(List<ExerciseWithSetCount> newExerciseList) {
        this.exerciseList = newExerciseList;
        notifyDataSetChanged(); // RecyclerView 갱신
    }

    // 특정 항목을 삭제하는 메서드 (UI 업데이트 포함)
    public void removeItem(int position) {
        if (position >= 0 && position < exerciseList.size()) {
            exerciseList.remove(position);
            notifyItemRemoved(position); // 해당 위치의 아이템이 삭제되었음을 알림
            notifyItemRangeChanged(position, exerciseList.size()); // 그 이후 아이템들의 위치 갱신
        }
    }

    // 현재 어댑터가 가지고 있는 Exercise 리스트를 반환
    public List<ExerciseWithSetCount> getCurrentList() {
        return exerciseList;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_item_layout, parent, false);
        return new RecordViewHolder(view);
    }

    public void updateSetCountById(String exerciseId, String newSetCount) {
        for (int i = 0; i < exerciseList.size(); i++) {
            if (exerciseList.get(i).getExercise().getExerciseId().equals(exerciseId)) {
                exerciseList.get(i).setSetCount(newSetCount);
                notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        ExerciseWithSetCount item = exerciseList.get(position);
        holder.bind(item);

        // RecyclerView 항목 전체 클릭 리스너
        holder.contentLayout.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(item.getExercise());
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(item.getExercise().getExerciseId(), position);
            }
        });
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String exerciseId, int position);
    }
    private OnDeleteClickListener deleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    // ViewHolder 클래스
    public static class RecordViewHolder extends RecyclerView.ViewHolder {
        ImageView exerciseImage;
        TextView koreanName;
        TextView category;
        TextView setCount;
        Button deleteButton;
        View contentLayout;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseImage = itemView.findViewById(R.id.exercise_img);
            koreanName = itemView.findViewById(R.id.korean_name);
            category = itemView.findViewById(R.id.category);
            setCount = itemView.findViewById(R.id.setCount);
            deleteButton = itemView.findViewById(R.id.btnDelete);
            contentLayout = itemView.findViewById(R.id.item_content);
        }

        public void bind(ExerciseWithSetCount item) {
            Exercise exercise = item.getExercise();
            exerciseImage.setImageResource(exercise.getImageResId());
            koreanName.setText(exercise.getKoreanName());
            category.setText("분류: " + exercise.getCategory());
            setCount.setText(item.getSetCount());

        }
    }
}
