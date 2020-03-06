package com.baeldung.jenkins.plugins;

public class Component {

    private String text;

    public Component(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
