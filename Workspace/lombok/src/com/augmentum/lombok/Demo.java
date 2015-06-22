package com.augmentum.lombok;

public class Demo {

    public static void main(String[] args) {
        Mountain mountain = new Mountain(32.2, 23.4);

        mountain.setContry("America");
        mountain.setName("Beauty");
        System.out.println(mountain.toString());
    }

}
