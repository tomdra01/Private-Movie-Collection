package be;

import java.time.LocalDate;

public class Movie {
    private int id = 0;
    private String name;
    private double rating;
    private String fileLink;
    private int release;
    private LocalDate lastView;

    /**
     * Constructor for Movie
     */
    public Movie(String name, double rating, String fileLink, int release, LocalDate lastView) {
        this.name = name;
        this.rating = rating;
        this.fileLink = fileLink;
        this.release = release;
        this.lastView = lastView;
    }

    /**
     * Constructor for MovieDAO
     */
    public Movie(int id, String name, double rating, String fileLink, int release, LocalDate lastView) {
        this(name, rating, fileLink, release, lastView);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public LocalDate getLastView() {
        return lastView;
    }

    public void setLastView(LocalDate lastView) {
        this.lastView = lastView;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", fileLink='" + fileLink + '\'' +
                ", release=" + release +
                ", lastView=" + lastView +
                '}';
    }
}
