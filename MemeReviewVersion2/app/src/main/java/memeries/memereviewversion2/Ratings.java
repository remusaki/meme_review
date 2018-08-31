package memeries.memereviewversion2;

/**
 * Created by Remusaki on Jun 2 2018.
 */

public class Ratings {
    private String lastname, firstname, rating, rating_comment;

    public Ratings(String lastname, String firstname, String rating, String rating_comment) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.rating = rating;
        this.rating_comment = rating_comment;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getRating() {
        return rating;
    }

    public String getRating_comment() {
        return rating_comment;
    }
}
