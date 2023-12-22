package com.webnote.webnotebook.dao.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notes_generator")
    @SequenceGenerator(name = "notes_generator", sequenceName = "notes_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public Note() {


    }

    public Note(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Note(int id, String title, String content, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && Objects.equals(title, note.title) && Objects.equals(content, note.content) && Objects.equals(author, note.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, author);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                '}';
    }
}