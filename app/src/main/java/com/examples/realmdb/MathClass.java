package com.examples.realmdb;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MathClass extends RealmObject {

    int classId;
    String subject;
    Person teacher;
    RealmList<Person> students;

    public MathClass() {
    }

    public MathClass(int classId, String subject, Person teacher, RealmList<Person> students) {
        this.classId = classId;
        this.subject = subject;
        this.teacher = teacher;
        this.students = students;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public List<Person> getStudents() {
        return students;
    }

    public void setStudents(RealmList<Person> students) {
        this.students = students;
    }
}
