package com.augmentum.lombok;

import lombok.Data;

@Data
public class Mountain {

    private String contry;
    private String name;

    // final类型的fields没有set方法，并且在构造方法中必须进行初始化。
    private final double latitude;
    private final double longtitude;

}
