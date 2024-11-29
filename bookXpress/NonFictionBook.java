package bookXpress;
public class NonFictionBook extends Book {
    String subject;

    public NonFictionBook(int copiesAvailable, int cost, String subject) {
        super(copiesAvailable, cost); // Call to the parent class constructor
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "NonFictionBook{" +
                "subject='" + subject + '\'' +
                ", copiesAvailable=" + copiesAvailable +
                ", cost=" + cost +
                '}';
    }
}
