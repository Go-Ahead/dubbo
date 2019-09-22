/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.demo.consumer;

import Entity.Person;
import com.alibaba.dubbo.demo.DemoService;
import com.alibaba.dubbo.demo.DemoService2;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Consumer {

    public static void main(String[] args) {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
        context.start();
        final DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy
        final DemoService2 demoService2 = (DemoService2) context.getBean("demoService2"); // get remote service proxy

        int number = 1;
        ExecutorService executorService = Executors.newFixedThreadPool(number);
        ExecutorService executorService2 = Executors.newFixedThreadPool(number);
        for (int i=0;i < number;i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            String hello = demoService.sayHello("world"); // call remote method
                            System.out.println(hello); // get result
                            String hello2 = demoService.sayByeBye("china"); // call remote method
                            System.out.println(hello2); // get result

                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            });
        }


        final Person person = new Person();
        person.setName("李四");
        person.setAge(18);

        for(int i=0;i < number;i++) {
            executorService2.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            String name = demoService2.getName(person);
                            System.out.printf(name);

                            String print = demoService2.print(person);

                            System.out.printf(print);


                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            });
        }


        Scanner scanner = new Scanner(System.in);
        while (scanner.nextLine().equals("q")) {
            System.exit(0);
        }



    }
}
