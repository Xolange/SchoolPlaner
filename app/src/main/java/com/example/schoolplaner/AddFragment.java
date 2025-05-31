package com.example.schoolplaner;

import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Locale;

public class AddFragment extends Fragment {

    private EditText editTextTitle, editTextDescription;
    private TextView textViewTimePicker;
    private Spinner spinnerDay;
    private int selectedHour = -1;
    private int selectedMinute = -1;

    public AddFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        textViewTimePicker = view.findViewById(R.id.textViewTimePicker);
        spinnerDay = view.findViewById(R.id.spinnerDay);
        Button buttonSave = view.findViewById(R.id.buttonSave);

        // Настройка спиннера
        String[] days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, days);
        spinnerDay.setAdapter(adapter);

        textViewTimePicker.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (view1, hourOfDay, minute1) -> {
                        selectedHour = hourOfDay;
                        selectedMinute = minute1;
                        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                        textViewTimePicker.setText(formattedTime);
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        buttonSave.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();
            String day = spinnerDay.getSelectedItem().toString();
            String time = textViewTimePicker.getText().toString();

            if (!title.isEmpty() && !time.equals("Выбрать время")) {
                Task newTask = new Task(title, description, time, day);
                TaskStorage.addTask(getContext(), newTask);

                editTextTitle.setText("");
                editTextDescription.setText("");
                textViewTimePicker.setText("Выбрать время");
                spinnerDay.setSelection(0);
            }
        });

        return view;
    }
}
