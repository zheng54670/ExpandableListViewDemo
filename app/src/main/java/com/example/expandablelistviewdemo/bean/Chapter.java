package com.example.expandablelistviewdemo.bean;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
    private int id;
    private String name;

    public static final String TABLE_NAME = "tb_chapter";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";

    private List<ChapterItem> children = new ArrayList<>();

    public Chapter() {
    }

    public Chapter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(ChapterItem chapterItem) {
        chapterItem.setPid(getId());
        children.add(chapterItem);
    }

    public void addChild(int childId, String childName) {
        ChapterItem chapterItem = new ChapterItem(childId, childName);
        chapterItem.setPid(getId());
        children.add(chapterItem);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChapterItem> getChildren() {
        return children;
    }

    public void setChildren(List<ChapterItem> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
