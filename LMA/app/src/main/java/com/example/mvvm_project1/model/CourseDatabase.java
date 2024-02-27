package com.example.mvvm_project1.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

@Database(entities = {Category.class, Course.class}, version = 1)
public abstract class CourseDatabase extends RoomDatabase {
    public abstract CourseDAO getCourseDAO();
    public abstract CategoryDAO getCategoryDAO();

    //singleton pattern
    private static CourseDatabase instance;

    public static synchronized CourseDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CourseDatabase.class, "courses_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // Callbacks
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Insert data when app first launched...
            InitialiseData();


        }
    };

    private static void InitialiseData() {
        CourseDAO courseDAO = instance.getCourseDAO();
        CategoryDAO categoryDAO = instance.getCategoryDAO();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Categories
                Category category1 = new Category();
                category1.setCategoryName("Front End");
                category1.setCategoryDescription("Web Development Interface");

                Category category2 = new Category();
                category2.setCategoryName("Back End");
                category2.setCategoryDescription("Web Development Database");

                categoryDAO.insert(category1);
                categoryDAO.insert(category2);

                // Courses
                Course course1 = new Course();
                course1.setCourseName("HTML");
                course1.setUnitPrice("100$");
                course1.setCourseId(1);

                Course course2 = new Course();
                course2.setCourseName("CSS");
                course2.setUnitPrice("150$");
                course2.setCourseId(1);

                Course course3 = new Course();
                course3.setCourseName("PHP");
                course3.setUnitPrice("450$");
                course3.setCourseId(2);

                Course course4 = new Course();
                course4.setCourseName("AJAX");
                course4.setUnitPrice("300$");
                course4.setCourseId(2);

                courseDAO.insert(course1);
                courseDAO.insert(course2);
                courseDAO.insert(course3);
                courseDAO.insert(course4);

            }
        });
    }
}
