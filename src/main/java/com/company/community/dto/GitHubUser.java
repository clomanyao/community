package com.company.community.dto;

public class GitHubUser {
    private String name;
    private Long id;
    private String Bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", Bio='" + Bio + '\'' +
                '}';
    }
}
