package be;

public class Movie {
    private int id;
    private String name;
    private double rating;
    private String fileLink;
    private double lastView;

    public Movie(int id, String name, double rating, String fileLink, double lastView) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.fileLink = fileLink;
        this.lastView = lastView;
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

    public double getLastView() {
        return lastView;
    }

    public void setLastView(double lastView) {
        this.lastView = lastView;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", fileLink='" + fileLink + '\'' +
                ", lastView=" + lastView +
                '}';
    }
}
