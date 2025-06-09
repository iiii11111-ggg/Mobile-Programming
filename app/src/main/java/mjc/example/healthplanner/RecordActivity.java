package mjc.example.healthplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mjc.example.healthplanner.Request.NameRequest;

public class RecordActivity extends AppCompatActivity {

    private AutoCompleteTextView actvSearch;
    private RecyclerView exerciseListView;
    private TextView noResults;

    private ExerciseListAdapter exerciseListAdapter;
    private List<Exercise> allExercises;
    private TextView currentSelectedTab;

    private List<TextView> categoryTabs;
    private ImageView calender;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        actvSearch = findViewById(R.id.actv_search);
        exerciseListView = findViewById(R.id.exercise_list);
        noResults = findViewById(R.id.no_results);
        calender = findViewById(R.id.calendarMove);

        exerciseListAdapter = new ExerciseListAdapter(); // 어댑터 생성
        exerciseListView.setLayoutManager(new LinearLayoutManager(this)); //
        exerciseListView.setAdapter(exerciseListAdapter); // 어댑터 연결
        allExercises = new ArrayList<>();
        categoryTabs = new ArrayList<>();
        fab = findViewById(R.id.fab_button);

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray exerciseIdArray = response.getJSONArray("exerciseId");
                    JSONArray exerciseNameArray = response.getJSONArray("exerciseNameArray");
                    JSONArray exercisePartArray = response.getJSONArray("exerciseTypeArray");
                    JSONArray koreanNameArray = response.getJSONArray("koreanNameArray");

                    for(int i = 0; i < exerciseIdArray.length(); i++){
                        String id = exerciseIdArray.getString(i);
                        String englishName = exerciseNameArray.getString(i);
                        String part = exercisePartArray.getString(i);
                        String korean = koreanNameArray.getString(i);

                        int imageResId = getResources().getIdentifier(englishName, "drawable", getPackageName());

                        // 만약 리소스 ID를 찾지 못했다면 기본 이미지를 사용
                        if (imageResId == 0) {
                            imageResId = R.drawable.selected_calendar;
                        }
                        allExercises.add(new Exercise(englishName, korean, part, imageResId,id));
                    }
                    exerciseListAdapter.submitList(new ArrayList<>(allExercises));
                    ExerciseAutoCompleteAdapter autoCompleteAdapter = new ExerciseAutoCompleteAdapter(RecordActivity.this, allExercises);
                    actvSearch.setAdapter(autoCompleteAdapter);

                    setupCategoryTabs();
                    TextView initialTab = findViewById(R.id.tab_all);
                    selectTab(initialTab, "전체");
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(RecordActivity.this, "데이터 파싱 오류: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RecordActivity.this, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        };
        NameRequest NameRequest = new NameRequest(responseListener,errorListener);
        RequestQueue queue = Volley.newRequestQueue(RecordActivity.this);
        queue.add(NameRequest);

        // AutoCompleteTextView 텍스트 변경 리스너 (실시간 검색 필터링)
        actvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 사용자가 텍스트를 입력할 때마다 RecyclerView 업데이트
                // 탭이 선택된 상태와 관계없이 검색어를 입력하면 검색 결과만 보여주도록 로직 변경
                filterExercisesBySearchQuery(s.toString());
                // 검색어가 입력되면 탭 선택 상태를 해제하거나 '전체' 탭으로 초기화하는 UI 로직도 고려할 수 있음
                if (currentSelectedTab != null && !s.toString().isEmpty()) {
                    currentSelectedTab.setSelected(false);
                    currentSelectedTab = null; // 탭 선택 해제
                } else if (currentSelectedTab == null && s.toString().isEmpty()){
                    // 검색어가 비었고, 탭이 선택되지 않은 상태라면 '전체' 탭으로 다시 설정
                    TextView initialTab = findViewById(R.id.tab_all);
                    selectTab(initialTab, "전체");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // AutoCompleteTextView 아이템 클릭 리스너 (기존 코드 유지)
        actvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise selectedExercise = (Exercise) parent.getItemAtPosition(position);
                // 선택된 운동으로 RecyclerView를 필터링 (선택된 운동만 표시)
                List<Exercise> filtered = new ArrayList<>();
                filtered.add(selectedExercise);
                exerciseListAdapter.submitList(filtered);
                updateNoResultsVisibility(filtered.isEmpty());
                // AutoCompleteTextView에서 아이템 선택 시, 탭 선택 상태를 해제
                if (currentSelectedTab != null) {
                    currentSelectedTab.setSelected(false);
                    currentSelectedTab = null;
                }
            }
        });

        // RecyclerView 아이템 클릭 리스너 (기존 코드 유지)
        exerciseListAdapter.setOnItemClickListener(new ExerciseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(RecordActivity.this);
                dlg.setTitle(exercise.getKoreanName())
                .setMessage("추가하시겠습니까?")
                .setNegativeButton("취소",null)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(RecordActivity.this,ListActivity.class);
                        intent.putParcelableArrayListExtra("exerciseList",(ArrayList<Exercise>)allExercises);
                        intent.putExtra("selectedExercise",exercise);
                        startActivity(intent);
                    }
                }).show();
            }
        });
        // fab 버튼 리스터
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity.this,ListActivity.class);
                intent.putParcelableArrayListExtra("exerciseList",(ArrayList<Exercise>)allExercises);
                startActivity(intent);
            }
        });


    }

    // 새롭게 추가되는 메서드: 탭들을 초기화하고 클릭 리스너를 설정
    private void setupCategoryTabs() {
        // 모든 탭 TextView를 찾아 리스트에 추가
        categoryTabs.add(findViewById(R.id.tab_all));
        categoryTabs.add(findViewById(R.id.tab_leg));
        categoryTabs.add(findViewById(R.id.tab_back));
        categoryTabs.add(findViewById(R.id.tab_chest));
        categoryTabs.add(findViewById(R.id.tab_arm));
        categoryTabs.add(findViewById(R.id.tab_shoulder));
        categoryTabs.add(findViewById(R.id.tab_cardio));

        // 각 탭에 클릭 리스너 설정
        // 텍스트와 실제 필터링에 사용할 카테고리 문자열을 매핑
        // 이미지에서 '전체' 탭의 텍스트가 '전체'이고, 나머지는 한글 이름이므로, 카테고리 문자열과 일치시켜야 합니다.
        // 예를 들어 '하체' 탭은 'Leg' 카테고리를 필터링해야 합니다.
        // 따라서 `tabAll`은 "전체", `tabLeg`는 "Leg" 등과 같이 명시적으로 매핑합니다.

        // 클릭 리스너 설정
        for (TextView tab : categoryTabs) {
            tab.setOnClickListener(v -> {
                String tabText = ((TextView) v).getText().toString();
                String categoryToFilter;

                // 탭 텍스트에 따라 실제 카테고리 문자열 결정
                switch (tabText) {
                    case "전체":
                        categoryToFilter = "전체";
                        break;
                    case "하체":
                        categoryToFilter = "Leg";
                        break;
                    case "등":
                        categoryToFilter = "Back";
                        break;
                    case "가슴":
                        categoryToFilter = "Chest";
                        break;
                    case "팔":
                        categoryToFilter = "Arm";
                        break;
                    case "어깨":
                        categoryToFilter = "Shoulder";
                        break;
                    case "유산소":
                        categoryToFilter = "Cardio";
                        break;
                    default:
                        categoryToFilter = "전체"; // 기본값
                        break;
                }
                selectTab((TextView) v, categoryToFilter);
            });
        }
    }

    // 새롭게 추가되는 메서드: 탭 선택 및 RecyclerView 갱신 로직
    private void selectTab(TextView selectedTab, String category) {
        // 이전에 선택된 탭이 있다면 선택 해제
        if (currentSelectedTab != null) {
            currentSelectedTab.setSelected(false);
        }

        // 새 탭을 선택 상태로 설정
        selectedTab.setSelected(true);
        currentSelectedTab = selectedTab;

        // AutoCompleteTextView의 검색어를 초기화 (탭 선택 시 검색어 초기화)
        actvSearch.setText(""); // 검색어 초기화
        actvSearch.clearFocus(); // 포커스 제거
        // 키보드 숨기기 (선택 사항)
        // InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(actvSearch.getWindowToken(), 0);


        // 어댑터에 필터링 요청
        filterExercisesByCategory(category);
    }

    // 기존 filterExercises 메서드를 `filterExercisesBySearchQuery`로 변경 및 내용 수정
    private void filterExercisesBySearchQuery(String query) {
        String lowerCaseQuery = query.toLowerCase().trim(); // 공백 제거 추가
        List<Exercise> filteredList = new ArrayList<>();

        if (lowerCaseQuery.isEmpty()) {
            // 검색어가 비어있을 때, 현재 선택된 탭의 카테고리로 필터링된 목록을 보여줌
            // 탭이 선택되지 않은 경우 '전체'로 간주
            if (currentSelectedTab != null && currentSelectedTab.getText().toString().equals("전체")) {
                // '전체' 탭이 선택된 경우, allExercises를 직접 사용
                filteredList.addAll(allExercises);
            } else if (currentSelectedTab != null){
                // 특정 탭이 선택된 경우 해당 탭의 카테고리로 필터링
                String categoryToFilter = getCategoryStringFromTabText(currentSelectedTab.getText().toString());
                for (Exercise exercise : allExercises) {
                    if (exercise.getCategory().equalsIgnoreCase(categoryToFilter)) {
                        filteredList.add(exercise);
                    }
                }
            } else {
                // 어떤 탭도 선택되지 않았고, 검색어도 없는 경우 (초기 상태나 탭 초기화 후)
                filteredList.addAll(allExercises); // 기본적으로 전체 보여줌
            }

        } else {
            // 검색어가 있을 경우, 전체 목록에서 검색어에 따라 필터링
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

    // 새롭게 추가되는 메서드: 카테고리로 필터링
    private void filterExercisesByCategory(String category) {
        List<Exercise> filteredList = new ArrayList<>();

        if (category.equals("전체")) {
            filteredList.addAll(allExercises);
        } else {
            for (Exercise exercise : allExercises) {
                // 대소문자 구분 없이 비교
                if (exercise.getCategory().equalsIgnoreCase(category)) {
                    filteredList.add(exercise);
                }
            }
        }
        exerciseListAdapter.submitList(filteredList);
        updateNoResultsVisibility(filteredList.isEmpty());
        // 탭으로 필터링 시에는 검색어가 없으므로 "검색 결과 없음" 조건에서 !lowerCaseQuery.isEmpty()는 제거
    }


    // "검색 결과 없음" 텍스트 뷰의 가시성을 업데이트하는 메서드 (기존 코드 유지)
    private void updateNoResultsVisibility(boolean showNoResults) {
        if (showNoResults) {
            noResults.setVisibility(View.VISIBLE);
            exerciseListView.setVisibility(View.GONE); // 이름 변경 반영
        } else {
            noResults.setVisibility(View.GONE);
            exerciseListView.setVisibility(View.VISIBLE); // 이름 변경 반영
        }
    }

    // 탭 텍스트로부터 실제 카테고리 문자열을 얻는 헬퍼 메서드
    private String getCategoryStringFromTabText(String tabText) {
        switch (tabText) {
            case "전체":
                return "전체";
            case "하체":
                return "Leg";
            case "등":
                return "Back";
            case "가슴":
                return "Chest";
            case "팔":
                return "Arm";
            case "어깨":
                return "Shoulder";
            case "유산소":
                return "Cardio";
            default:
                return "전체"; // 기본값
        }
    }
}


