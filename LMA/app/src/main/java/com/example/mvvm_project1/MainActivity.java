package com.example.mvvm_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mvvm_project1.databinding.ActivityMainBinding;
import com.example.mvvm_project1.model.Category;
import com.example.mvvm_project1.model.Course;
import com.example.mvvm_project1.model.viewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private MainActivityClickHandlers clickHandlers;
    private ArrayList<Category> categoriesList;
    private ActivityMainBinding activityMainBinding;

    private Category selectedCategory;

    // Recycler view

    private RecyclerView courseRecyclerView;
    private  CourseAdapter courseAdapter;

    private ArrayList<Course> coursesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        clickHandlers = new MainActivityClickHandlers();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setClickHandlers(clickHandlers);




        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesList = (ArrayList<Category>) categories;

                for (Category c : categories) {
                    Log.i("TAG", c.getCategoryName() + "");
                }

                showOnSpinner();
            }
        });

        mainActivityViewModel.getCoursesOfSelectedCategory(1).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                for (Course c : courses) {
                    Log.v("TAG", c.getCourseName());
                }
            }
        });
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoriesList
        );

        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    public void LoadCoursesArrayList(int categoryID){
        mainActivityViewModel.getCoursesOfSelectedCategory(categoryID).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                coursesList = (ArrayList<Course>) courses;
                LoadRecyclerView();
            }
        });
    }

    private void LoadRecyclerView() {
        courseRecyclerView = activityMainBinding.secondaryLayout.recyclerview;
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerView.setHasFixedSize(true);

        courseAdapter = new CourseAdapter();
        courseRecyclerView.setAdapter(courseAdapter);

        courseAdapter.setCourses(coursesList);

    }

    public class MainActivityClickHandlers{

        public void onFabClicked(View view){
            Toast.makeText(MainActivity.this, "FAB is clicked", Toast.LENGTH_SHORT).show();
        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id){
            selectedCategory = (Category) parent.getItemAtPosition(pos);
            String message = "id is: " + selectedCategory.getId() +
                    "\n name is" + selectedCategory.getCategoryName();

            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            LoadCoursesArrayList(selectedCategory.getId());
        }
    }
}