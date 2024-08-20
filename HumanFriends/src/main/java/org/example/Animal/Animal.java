package org.example.Animal;

// Animal.java

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Animal {
    protected String name;
    protected Date birthDay;
    protected List<String> commands;

    public Animal(String name, Date birthDay) {
        this.name = name;
        this.birthDay = birthDay;
        this.commands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    @Override
    public String toString() {
        return name + " (" + birthDay + ")";
    }
}