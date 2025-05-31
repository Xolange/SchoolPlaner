package com.example.schoolplaner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private Spinner classSpinner, daySpinner;
    private RecyclerView lessonRecyclerView;
    private LessonsAdapter lessonsAdapter;

    private final String[] classes = {"7А", "8А", "8Б", "9А", "9Б", "10А", "10Б", "10В", "11А", "11Б", "11В"};
    private final String[] days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница"};

    private DatabaseReference databaseRef;

    public ScheduleFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        classSpinner = view.findViewById(R.id.classSpinner);
        daySpinner = view.findViewById(R.id.daySpinner);
        lessonRecyclerView = view.findViewById(R.id.lessonRecyclerView);

        databaseRef = FirebaseDatabase.getInstance().getReference("schedule");

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, classes);
        classSpinner.setAdapter(classAdapter);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, days);
        daySpinner.setAdapter(dayAdapter);

        lessonsAdapter = new LessonsAdapter(new ArrayList<>());
        lessonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lessonRecyclerView.setAdapter(lessonsAdapter);

        // Загрузка при выборе класса или дня
        View.OnClickListener updateListener = v -> loadLessons();
        classSpinner.setOnItemSelectedListener((android.widget.AdapterView.OnItemSelectedListener) new SimpleListener(updateListener));
        daySpinner.setOnItemSelectedListener((android.widget.AdapterView.OnItemSelectedListener) new SimpleListener(updateListener));

        return view;
    }

    private void loadLessons() {
        String selectedClass = classSpinner.getSelectedItem().toString();
        String selectedDay = daySpinner.getSelectedItem().toString();

        databaseRef.child(selectedClass).child(selectedDay)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Lesson> lessons = new ArrayList<>();
                        for (DataSnapshot lessonSnap : snapshot.getChildren()) {
                            Lesson lesson = lessonSnap.getValue(Lesson.class);
                            if (lesson != null) {
                                lessons.add(lesson);
                            }
                        }
                        lessonsAdapter.updateLessons(lessons);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // log error
                    }
                });
    }
}
