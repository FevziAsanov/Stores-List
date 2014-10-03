package model;

/**
 * Created by fevzi on 30.09.14.
 */
public class Product {

    private int id=0,lng,lat;
    private String title,description,avatar;
    private Author author;

    public int getId() {
        return id;
    }

    public int getLng() {
        return lng;
    }



    public String getAvatar() {

        return avatar;
    }

    public int getLat() {
        return lat;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(Author a) {
        this.author = a;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
