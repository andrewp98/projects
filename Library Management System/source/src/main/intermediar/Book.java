package ro.utcluj.intermediar;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

@Entity
@Table( name = "Book" )
public class Book extends Observable implements Serializable {

    private static final long serialVersionUID = -2487806939504038611L;
    private int id;
    private String genre;
    private String title;
    private String author;
    private int quantity;
    private int rating;
    private ArrayList<Account> inAsteptare;
    private List<String> review;

    public Book(){
        // this form used by Hibernate
    }

    public Book(String genre, String title, String author, int quantity) {
        this.genre = genre;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<Account> getInAsteptare() {
        return inAsteptare;
    }

    public void setInAsteptare(ArrayList<Account> inAsteptare) {
        this.inAsteptare = inAsteptare;
    }

    public void addWait(Account acc){
        this.inAsteptare.add(acc);
    }

    public void removeWait(Account acc){
        this.inAsteptare.remove(acc);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Transient
    public List<String> getReview() {
        return review;
    }

    public void setReview(List<String> review) {
        this.review = review;
    }

    public void notifyAvb(String message){
        setChanged();
        notifyObservers(message);
    }
}
