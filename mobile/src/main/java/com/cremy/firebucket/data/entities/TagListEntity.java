package com.cremy.firebucket.data.entities;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;

public final class TagListEntity {


    private HashMap<String, String> tags = null;

    public TagListEntity() {
        // Intentionally empty.
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    @Exclude
    public boolean isEmpty() {
        return this.tags.isEmpty();
    }

    @Exclude
    public String[] toArray() {
        ArrayList<String> list = new ArrayList<String>(this.tags.values());
        String[] strings = list.toArray(new String[this.tags.size()]);
        return strings;
    }

}