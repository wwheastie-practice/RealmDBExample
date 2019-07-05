package com.examples.realmdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Person teacher;
    RealmList<Person> students = new RealmList<>();
    RealmList<Person> testStudents;
    MathClass mathClass;
    Realm mRealm;
    TextView classInfo;
    Button writeButton;
    Button readButton;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeActivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        deleteDatabase();
    }

    private void intializeActivity() {
        initializeViewObjects();
        intializeListeners();
        intializeRealmDB();
    }

    private void initializeViewObjects() {
        classInfo = findViewById(R.id.classInfoTxt);
        writeButton = findViewById(R.id.writeBtn);
        readButton = findViewById(R.id.readBtn);
        deleteButton = findViewById(R.id.deleteBtn);
    }

    private void intializeListeners() {
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performWriteButtonClick();
            }
        });
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performReadButtonClick();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performDeleteButtonClick();
            }
        });
    }

    private void intializeRealmDB() {
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();
    }

    private void performWriteButtonClick() {
        setupClassroom();
        writeToDatabase();
    }

    private void performReadButtonClick() {
        //readFromDatabase();
        readFromDatabaseTwo();
        setupNestedScrollView();
    }

    private void performDeleteButtonClick() {
        deleteDatabase();
    }

    private void writeToDatabase() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mathClass);
            }
        });
    }

    private void readFromDatabase() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mathClass = realm.where(MathClass.class).equalTo("classId", 1).findFirst();
            }
        });
    }

    private void readFromDatabaseTwo() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Person> results = realm.where(Person.class).equalTo("firstName", "LeBron").findAll();
            }
        });
    }

    private void deleteDatabase() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    private void setupNestedScrollView() {
        String scrollViewText = createScrollViewContent();
        classInfo.setText("");
        classInfo.append(scrollViewText);
    }

    private String createScrollViewContent() {
        StringBuilder content = new StringBuilder();
        content.append("Subject: " + mathClass.getSubject() + "\n");
        content.append("Teacher: " + mathClass.getTeacher().getFirstName() + " " + mathClass.getTeacher().getLastName() + "\n");
        content.append("Students: " + "\n");
        for(Person student : mathClass.getStudents()) {
            content.append("\t\t" + student.getFirstName() + " " + student.getLastName() + "\n");
        }
        return content.toString();
    }

    private void setupClassroom() {
        createTeacher();
        createStudents();
        createClassroom();
    }

    private void createTeacher() {
        teacher = new Person("Dianna", "Ross", 36);
    }

    private void createStudents() {
        students.add(new Person("Khawi", "Leonard", 19));
        students.add(new Person("Anthony", "Davis", 18));
        students.add(new Person("Lebron", "James", 20));
    }

    private void createClassroom() {
        mathClass = new MathClass(1, "Calculus", teacher, students);
    }
}
