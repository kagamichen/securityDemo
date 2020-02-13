package org.chen.securitydemo;

import java.util.ArrayList;
import java.util.List;

public class stringbuffertest {
    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add(0,"哈哈");
        test.add(1,"嘻嘻");

        System.out.println(test.toString());
        System.out.println(test.size());
        StringBuffer stringBuffer = new StringBuffer();
    }
}
