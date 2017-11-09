package ru.javawebinar.topjava.dao;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Urban Aleksandr on 09.11.2017
 */
public class CounterHolder {
    public volatile static AtomicInteger counter = new AtomicInteger();
}
