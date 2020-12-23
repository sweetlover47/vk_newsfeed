package com.example.vk_try2;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

class Profile implements Serializable {
    public int id;
    public String first_name;
    public String last_name;
    public String photo_100;
}

class Group implements Serializable {
    public int id;
    public String name;
    public String photo_100;
}

class Item {
    public int source_id;
    public long date;
    public String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return source_id == item.source_id &&
                date == item.date &&
                Objects.equals(text, item.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source_id, date, text);
    }
}

class Resp {
    public List<Item> items;
    public List<Profile> profiles;
    public List<Group> groups;
}

public class VkApiResponse {
    Resp response;
}
