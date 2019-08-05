package com.jyw.lq.tryresource;

import java.io.FileInputStream;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        openFileWithTryResource();
//        openFileWithTryFinally();
    }


    public static void openFileWithTryResource() throws IOException {
        try(FileInputStream f = new FileInputStream("")){
            f.read();
        }
    }

    public static void openFileWithTryFinally() throws IOException {
        FileInputStream f =  new FileInputStream("");
        try{
            f.read();
        }finally {
            int a = 0/10;
            f.close();
        }
    }
}
