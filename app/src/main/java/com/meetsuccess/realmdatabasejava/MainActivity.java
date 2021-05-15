package com.meetsuccess.realmdatabasejava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edit text
    private EditText courseNameEdt, courseDurationEdt, courseDescriptionEdt, courseTracksEdt;
    private Realm realm;

    // creating a strings for storing
    // our values from edittext fields.
    private String courseName, courseDuration, courseDescription, courseTracks;

    //read data
    List<DataModal> dataModals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our edittext and buttons
        realm = Realm.getDefaultInstance();
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration);

        // creating variable for button
        Button submitCourseBtn = findViewById(R.id.idBtnAddCourse);
        courseTracksEdt = findViewById(R.id.idEdtCourseTracks);
        findViewById(R.id.readData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadDataFromRealm();
            }
        });
        findViewById(R.id.updatatdata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DataModal modal = realm.where(DataModal.class).equalTo("id", 1).findFirst();

                updateCourse(modal, courseName, courseDescription, courseDuration, courseTracks);

            }
        });
        findViewById(R.id.deletedata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DataModal modal = realm.where(DataModal.class).equalTo("id", 1).findFirst();

                deleteCourse(1);
            }
        });

        submitCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                courseName = courseNameEdt.getText().toString();
                courseDescription = courseDescriptionEdt.getText().toString();
                courseDuration = courseDurationEdt.getText().toString();
                courseTracks = courseTracksEdt.getText().toString();


                if (TextUtils.isEmpty(courseName)) {
                    courseNameEdt.setError("Please enter Course Name");
                } else if (TextUtils.isEmpty(courseDescription)) {
                    courseDescriptionEdt.setError("Please enter Course Description");
                } else if (TextUtils.isEmpty(courseDuration)) {
                    courseDurationEdt.setError("Please enter Course Duration");
                } else if (TextUtils.isEmpty(courseTracks)) {
                    courseTracksEdt.setError("Please enter Course Tracks");
                } else {
                    // calling method to add data to Realm database..
                    addDataToDatabase(courseName, courseDescription, courseDuration, courseTracks);
                    Toast.makeText(MainActivity.this, "Course added to database..", Toast.LENGTH_SHORT).show();
                    courseNameEdt.setText("");
                    courseDescriptionEdt.setText("");
                    courseDurationEdt.setText("");
                    courseTracksEdt.setText("");
                }
            }
        });
    }
    private void updateCourse(DataModal modal, String courseName, String courseDescription, String courseDuration, String courseTracks) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                modal.setCourseDescription(courseDescription);
                modal.setCourseName(courseName);
                modal.setCourseDuration(courseDuration);
                modal.setCourseTracks(courseTracks);
                realm.copyToRealmOrUpdate(modal);
                Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ReadDataFromRealm() {
        realm = Realm.getDefaultInstance();
        RealmResults<DataModal> realmResults = realm.where(DataModal.class).findAll();
        Log.d("sizeofrealmdata","--"+realmResults.get(0).getId());
        Toast.makeText(MainActivity.this, "Read", Toast.LENGTH_SHORT).show();

    }
    // deleteCourse() function
    private void deleteCourse(long id) {
         RealmResults<DataModal> realmResults = realm.where(DataModal.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                 realmResults.deleteAllFromRealm();
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDataToDatabase(String courseName, String courseDescription, String courseDuration, String courseTracks) {

        DataModal modal = new DataModal();
        Number id = realm.where(DataModal.class).max("id");
        long nextId;

        // validating if id is null or not.
        if (id == null) {
            // if id is null
            // we are passing it as 1.
            nextId = 1;
        } else {
            // if id is not null then
            // we are incrementing it by 1
            nextId = id.intValue() + 1;
        }
        modal.setId(nextId);
        modal.setCourseDescription(courseDescription);
        modal.setCourseName(courseName);
        modal.setCourseDuration(courseDuration);
        modal.setCourseTracks(courseTracks);
        modal.setKeepRoom(true);

        // on below line we are calling a method to execute a transaction.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                // inside on execute method we are calling a method
                // to copy to real m database from our modal class.
                realm.insertOrUpdate(modal);

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
    void filterInrealm()
    {
//        RealmResults<Student> results = realm.where(Student.class)
//                .contains("Name", "some string")
//                .equalTo("isGraduated", false)
//                .findAllSorted("birthday",  Sort.ASCENDING);
        RealmResults<DataModal> results = realm.where(DataModal.class).greaterThanOrEqualTo("id", 25).sort("aks").findAll();
//useful queries
//        query.greaterThanOrEqualTo("id", startDate)
//                .findAll()
//                .where()
//                .lessThanOrEqualTo(RealmObject.FIELD_LINKED_OBJECT + "." + LinkedObject.FIELD_DATE, endDate)
//                .findAll();

    }
}