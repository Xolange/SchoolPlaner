package com.example.schoolplaner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleFragment extends Fragment {

    private Spinner spinnerDay;
    private RecyclerView recyclerViewLessons;
    private LessonsAdapter lessonsAdapter;
    private Map<String, List<Lesson>> scheduleMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        spinnerDay = view.findViewById(R.id.spinnerDaySchedule);
        recyclerViewLessons = view.findViewById(R.id.recyclerViewSchedule);
        recyclerViewLessons.setLayoutManager(new LinearLayoutManager(getContext()));

        lessonsAdapter = new LessonsAdapter(new ArrayList<>());
        recyclerViewLessons.setAdapter(lessonsAdapter);

        setupSchedule();
        setupSpinner();

        return view;
    }

    private void setupSchedule() {
        scheduleMap = new HashMap<>();

        List<Lesson> mondayLessons = new ArrayList<>();
        mondayLessons.add(new Lesson("Математика", "08:00 - 08:45"));
        mondayLessons.add(new Lesson("Русский язык", "08:55 - 09:40"));
        scheduleMap.put("Понедельник", mondayLessons);

        List<Lesson> tuesdayLessons = new ArrayList<>();
        tuesdayLessons.add(new Lesson("Литература", "08:00 - 08:45"));
        scheduleMap.put("Вторник", tuesdayLessons);

    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);

        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDay = parent.getItemAtPosition(position).toString();
                List<Lesson> lessons = scheduleMap.getOrDefault(selectedDay, new ArrayList<>());
                lessonsAdapter.setLessons(lessons);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
