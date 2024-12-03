public class FictionBook extends Book {
    String genre;

    public FictionBook(int copiesAvailable, double cost, String genre) {
        super(copiesAvailable, cost);
        this.genre = genre;
    }
}
