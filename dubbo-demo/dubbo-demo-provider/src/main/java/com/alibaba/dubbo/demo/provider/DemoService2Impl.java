package com.alibaba.dubbo.demo.provider;

import Entity.Person;
import com.alibaba.dubbo.demo.DemoService2;

public class DemoService2Impl implements DemoService2 {
    @Override
    public String print(Person person) {

        return person.toString();
    }

    @Override
    public String getName(Person person) {
        return person.getName();
    }
}
