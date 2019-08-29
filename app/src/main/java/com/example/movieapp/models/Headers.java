package com.example.movieapp.models;

public class Headers extends Movies {

    public Headers(String text, int position) {
        this.text = text;
        this.position = position;
    }

    private String text = "Default Text Test";
    private int position;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getType() {
        return HEADER_TYPE;
    }

    @Override
    public int getPosition() {
        return position;
    }
}
