package com.cremy.firebucket.domain.models;

import java.util.ArrayList;
import java.util.HashMap;

public final class TagListModel {

    private HashMap<String, String> tags = null;

    public TagListModel() {
        // Intentionally empty.
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public boolean isEmpty() {
        return this.tags.isEmpty();
    }

    public String[] toArray() {
        ArrayList<String> list = new ArrayList<String>(this.tags.values());
        String[] strings = list.toArray(new String[this.tags.size()]);
        return strings;
    }
}