package com.alibaba.dubbo.demo.provider;

import Entity.Person;
import com.alibaba.dubbo.demo.DemoService2;

public class DemoService2Impl implements DemoService2 {
    @Override
    public String print(Person person) {
        return null;
    }

    @Override
    public String getName(Person person) {
        return null;
    }
}
