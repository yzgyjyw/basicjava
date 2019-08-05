package com.jyw.lq.enumtype;

/*
* public final class com.jyw.lq.enumtype.Season extends java.lang.Enum<com.jyw.lq.enumtype.Season> {
  public static final com.jyw.lq.enumtype.Season SPRING;
  public static final com.jyw.lq.enumtype.Season SUMMER;
  public static final com.jyw.lq.enumtype.Season AUTUMN;
  public static final com.jyw.lq.enumtype.Season WINTER;
  public static com.jyw.lq.enumtype.Season[] values();
  public static com.jyw.lq.enumtype.Season valueOf(java.lang.String);
  static {};
}

* */
public enum Season {
    SPRING,
    SUMMER,
    AUTUMN,
    WINTER;

    Season(){
        System.out.println("init season");
    }

}
