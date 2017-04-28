package com.internshala.notesinternshala.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by piyush0 on 26/04/17.
 */

public class Note {
    String text;
    Date updatedAt;
    Integer id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Note(String text, Date updatedAt, Integer id) {
        this.text = text;
        this.updatedAt = updatedAt;
        this.id = id;
    }

    public Note(String text, Date updatedAt) {
        this.text = text;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static ArrayList<Note> getDummyNotes() {

        ArrayList<Note> notes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            notes.add(new Note("Abcd", new Date(), i));
        }

        return notes;
    }
}
