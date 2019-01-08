package com.dropwizardrethinkdb.service.api;

public class Person {


    private String name;
    private String greetingMessage;

    public Person() {
    }

    public Person(String name, String greetingMessage) {
        this.name = name;
        this.greetingMessage = greetingMessage;
    }

    public String getName() {
        return name;
    }

    public String getGreetingMessage() {
        return greetingMessage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGreetingMessage(String greetingMessage) {
        this.greetingMessage = greetingMessage;
    }

}
