package ru.specialist.java.fx;

public class Author {
    private int id;
    private String authorName;
    private String lastName;

    public Author(int id, String authorName, String lastName) {
        this.id = id;
        this.authorName = authorName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
