package mjc.example.healthplanner;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mjc.example.healthplanner.Request.ListRequest;

public class RecordActivity extends AppCompatActivity {

    private AutoCompleteTextView actvSearch;
    private RecyclerView exerciseList;
    private TextView noResults;

    private ExerciseListAdapter exerciseListAdapter;
    private List<Exercise> allExercises;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        actvSearch = findViewById(R.id.actv_search);
        exerciseList = findViewById(R.id.exercise_list);
        noResults = findViewById(R.id.no_results);

        exerciseListAdapter = new ExerciseListAdapter(); // 어댑터 생성
        exerciseList.setLayoutManager(new LinearLayoutManager(this)); //
        exerciseList.setAdapter(exerciseListAdapter); // 어댑터 연결
        allExercises = new ArrayList<>();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(RecommendActivity.this, "기록되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RecommendActivity.this, PlannerActivity.class);
                startActivity(intent);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RecommendActivity.this, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        };
        ListRequest ListRequest = new ListRequest(userId,exerciseList,setCountList,responseListener,errorListener);
        RequestQueue queue = Volley.newRequestQueue(RecommendActivity.this);
        queue.add(ListRequest);

        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));
        allExercises.add(new Exercise("overhead_press", "오버헤드 프레스", "어깨", R.drawable.ohp));

        exerciseListAdapter.submitList(new ArrayList<>(allExercises));

        ExerciseAutoCompleteAdapter autoCompleteAdapter = new ExerciseAutoCompleteAdapter(this, allExercises);
        actvSearch.setAdapter(autoCompleteAdapter);

        // AutoCompleteTextView 텍스트 변경 리스너 (실시간 검색 필터링)
        actvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 사용자가 텍스트를 입력할 때마다 RecyclerView 업데이트
                filterExercises(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // AutoCompleteTextView 아이템 클릭 리스너 (자동 완성 목록에서 항목 선택 시)
        actvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise selectedExercise = (Exercise) parent.getItemAtPosition(position);
                // 선택된 운동으로 RecyclerView를 필터링 (선택된 운동만 표시)
                List<Exercise> filtered = new ArrayList<>();
                filtered.add(selectedExercise);
                exerciseListAdapter.submitList(filtered);
                updateNoResultsVisibility(filtered.isEmpty());
                // 키보드 내리기 (선택 사항)
                // InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                // imm.hideSoftInputFromWindow(actvSearch.getWindowToken(), 0);
            }
        });

        // RecyclerView 아이템 클릭 리스너 (ExerciseListAdapter 내부에 정의한 인터페이스 사용)
        exerciseListAdapter.setOnItemClickListener(new ExerciseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Toast.makeText(RecordActivity.this, exercise.getKoreanName() + " (목록에서) 클릭됨", Toast.LENGTH_SHORT).show();
                // 여기에 클릭된 운동에 대한 추가 동작 (예: 새 화면으로 이동, 운동 추가 다이얼로그 띄우기 등)을 구현할 수 있습니다.
            }
        });
    }

    // RecyclerView에 표시될 데이터를 필터링하는 메서드
    private void filterExercises(String query) {
        String lowerCaseQuery = query.toLowerCase();
        List<Exercise> filteredList = new ArrayList<>();

        if (lowerCaseQuery.isEmpty()) {
            filteredList.addAll(allExercises); // 검색어가 없으면 전체 목록 표시
        } else {
            for (Exercise exercise : allExercises) {
                if (exercise.getKoreanName().toLowerCase().contains(lowerCaseQuery) ||
                        exercise.getEnglishName().toLowerCase().contains(lowerCaseQuery) ||
                        exercise.getCategory().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(exercise);
                }
            }
        }
        exerciseListAdapter.submitList(filteredList); // 필터링된 목록으로 RecyclerView 업데이트
        updateNoResultsVisibility(filteredList.isEmpty() && !lowerCaseQuery.isEmpty());
    }

    // "검색 결과 없음" 텍스트 뷰의 가시성을 업데이트하는 메서드
    private void updateNoResultsVisibility(boolean showNoResults) {
        if (showNoResults) {
            noResults.setVisibility(View.VISIBLE);
            exerciseList.setVisibility(View.GONE);
        } else {
            noResults.setVisibility(View.GONE);
            exerciseList.setVisibility(View.VISIBLE);
        }
    }

}
