/*
 * (C) Copyright 2018 Siyue Holding Group.
 */

public class InnerTestMain {

  int a = 1;//modeCount

  class InnerClass{
    private int b = a; //expect
  }

  public static void main(String[] args){
    InnerTestMain main = new InnerTestMain();
    InnerTestMain.InnerClass innerClass1 = main.new InnerClass();//new Iter
    System.out.println(main.a == innerClass1.b);

    main.a = 2;

    InnerTestMain.InnerClass innerClass2 = main.new InnerClass();//new Iter2

    System.out.println(innerClass2.b == main.a);  //b=2
  }
}
