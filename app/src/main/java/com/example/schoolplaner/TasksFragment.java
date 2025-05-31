package com.example.schoolplaner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    private TasksAdapter adapter;
    private List<Task> allTasks;         // Все задачи из хранилища
    private List<Task> filteredTasks;    // Отфильтрованные задачи по дню
    private TextView emptyStateTextView;
    private Spinner spinnerDayFilter;

    private final String[] daysOfWeek = {
            "Все дни",
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота",
            "Воскресенье"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        emptyStateTextView = view.findViewById(R.id.textViewEmpty);
        spinnerDayFilter = view.findViewById(R.id.spinnerDayFilter);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Загрузить все задачи из TaskStorage
        allTasks = TaskStorage.getTasks(getContext());
        filteredTasks = new ArrayList<>(allTasks);

        adapter = new TasksAdapter(filteredTasks);
        recyclerView.setAdapter(adapter);

        updateEmptyState();

        // Настройка спиннера для выбора дня
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                daysOfWeek
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDayFilter.setAdapter(spinnerAdapter);

        spinnerDayFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterTasksByDay(daysOfWeek[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Не нужно ничего делать
            }
        });

        // Добавляем возможность свайпа для удаления задачи
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // перемещение не нужно
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                // Удаляем задачу из обоих списков
                Task taskToRemove = filteredTasks.get(position);
                filteredTasks.remove(position);
                allTasks.remove(taskToRemove);

                // Сохраняем обновлённый список всех задач
                TaskStorage.saveTasks(getContext(), allTasks);

                adapter.notifyItemRemoved(position);
                updateEmptyState();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    private void filterTasksByDay(String selectedDay) {
        filteredTasks.clear();

        if (selectedDay.equals("Все дни")) {
            filteredTasks.addAll(allTasks);
        } else {
            for (Task task : allTasks) {
                if (task.getDay() != null && task.getDay().equals(selectedDay)) {
                    filteredTasks.add(task);
                }
            }
        }
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (filteredTasks.isEmpty()) {
            emptyStateTextView.setVisibility(View.VISIBLE);
        } else {
            emptyStateTextView.setVisibility(View.GONE);
        }
    }
}
