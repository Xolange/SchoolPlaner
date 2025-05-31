package com.example.schoolplaner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.schoolplaner.ScheduleDayFragment;


public class SchedulePagerAdapter extends FragmentStateAdapter {

    private String selectedClass;
    private String[] days;

    public SchedulePagerAdapter(@NonNull Fragment fragment, String selectedClass, String[] days) {
        super(fragment);
        this.selectedClass = selectedClass;
        this.days = days;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ScheduleDayFragment.newInstance(selectedClass, days[position]);
    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return (selectedClass + days[position]).hashCode();
    }

    @Override
    public boolean containsItem(long itemId) {
        for (String day : days) {
            if ((selectedClass + day).hashCode() == itemId) {
                return true;
            }
        }
        return false;
    }
}
