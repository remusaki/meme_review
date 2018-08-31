package memeries.memereviewversion2;

import android.support.annotation.NonNull;

/**
 * Created by djmurusaki on 14 May 2018.
 */

public class Memes implements Comparable<Memes>{
    private String memes_id, name, description, fullpath, timestamp, isApproved, category, rating;

    public Memes(String memes_id, String name, String description, String fullpath, String timestamp, String isApproved, String category, String rating){
        this.memes_id = memes_id;
        this.name = name;
        this.description = description;
        this.fullpath = fullpath;
        this.timestamp = timestamp;
        this.isApproved = isApproved;
        this.category = category;
        this.rating = rating;
    }

    public String getMemes_id() {
        return memes_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFullpath() {
        return fullpath;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public String getCategory() {
        return category;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public int compareTo(Memes other) {
        return rating.compareTo(other.rating);
    }
}
