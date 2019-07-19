package com.example.expandablelistviewdemo.bean;

import java.util.ArrayList;
import java.util.List;

public class ChapterLab {

    public static List<Chapter> generateMockData(){
        List<Chapter> data = new ArrayList<>();

        Chapter root1 = new Chapter(1,"Android");
        Chapter root2 = new Chapter(2,"IOS");
        Chapter root3 = new Chapter(3,"Linux");
        Chapter root4 = new Chapter(4,"Windows");

        root1.addChild(1,"Android child 1");
        root1.addChild(2,"Android child 2");
        root1.addChild(3,"Android child 3");
        root1.addChild(4,"Android child 4");
        root1.addChild(5,"Android child 5");

        root2.addChild(1,"IOS child 1");
        root2.addChild(2,"IOS child 2");
        root2.addChild(3,"IOS child 3");
        root2.addChild(4,"IOS child 4");
        root2.addChild(5,"IOS child 5");

        root3.addChild(1,"Linux child 1");
        root3.addChild(2,"Linux child 2");
        root3.addChild(3,"Linux child 3");
        root3.addChild(4,"Linux child 4");
        root3.addChild(5,"Linux child 5");

        root4.addChild(1,"Windows child 1");
        root4.addChild(2,"Windows child 2");
        root4.addChild(3,"Windows child 3");
        root4.addChild(4,"Windows child 4");
        root4.addChild(5,"Windows child 5");

        data.add(root1);
        data.add(root2);
        data.add(root3);
        data.add(root4);
        return data;
    }
}
