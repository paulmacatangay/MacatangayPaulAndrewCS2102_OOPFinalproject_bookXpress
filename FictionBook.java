package bookXpress;
public class FictionBook extends Book {
    String genre;

    public FictionBook(int copiesAvailable, int cost, String genre) {
        super(copiesAvailable, cost); // Call to the parent class constructor
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "FictionBook{" +
                "genre='" + genre + '\'' +
                ", copiesAvailable=" + copiesAvailable +
                ", cost=" + cost +
                '}';
    }
}
