package com.wang.service;

import com.wang.spring.Autowired;
import com.wang.spring.BeanNameAware;
import com.wang.spring.Component;
import com.wang.spring.InitializingBean;

@Component("userService")
public class UserService implements BeanNameAware, InitializingBean, UserInterface {

    @Autowired
    private OrderService orderService;

    private String beanName;

    @Override
    public void test() {
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet");
    }
}
