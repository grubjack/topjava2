package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.Role.ROLE_ADMIN;
import static ru.javawebinar.topjava.model.Role.ROLE_USER;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("Beverie", "bevequot0@state.tx.us", "favKFd", ROLE_USER, ROLE_ADMIN),
            new User("Bambie", "bkassel1@163.com", "sYLOId", ROLE_USER),
            new User("Sharl", "schieze2@usa.gov", "c4GDTIL", ROLE_USER),
            new User("Porty", "plevi3@earthlink.net", "P3aDDadX", ROLE_USER),
            new User("Zolly", "zgarden4@europa.eu", "ZX53HQGPx", ROLE_USER, ROLE_ADMIN),
            new User("Micheline", "mfurney5@geocities.com", "GGKMZFVzFaLi", ROLE_USER),
            new User("Yettie", "ymeaden6@oracle.com", "fNWfmrhNCmp", ROLE_USER),
            new User("Cristin", "chanham7@yale.edu", "uIhpW2AcTKj", ROLE_ADMIN),
            new User("Mattheus", "mstoving8@ebay.co.uk", "pnNrrOu6Nqjn", ROLE_USER),
            new User("Stavro", "srobbie9@whitehouse.gov", "TfVP3pmZRG", ROLE_USER)
    );
}