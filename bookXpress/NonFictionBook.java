public class NonFictionBook extends Book {
    String subject;

    public NonFictionBook(int copiesAvailable, double cost, String subject) {
        super(copiesAvailable, cost);
        this.subject = subject;
    }
}
