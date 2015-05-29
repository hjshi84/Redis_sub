package com.company;

import java.util.ArrayList;

public class Main {
    static String addr="10.200.46.245";
    static int port=6379;

    public static void main(String[] args) {
	// write your code her

    int num=0;
    SensordbSub myssb=new SensordbSub(addr,port);
    while(myssb.listen()){

        ArrayList<String> aa=new ArrayList<String>();
              aa=  myssb.getRead();
        for(String i :aa) {
            num++;
        }
        System.err.print(num+"\n");
        //System.err.print(myssb.getRead()+"\n");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    myssb.destroy();

    }

}
