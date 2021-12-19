package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

public static final List<User> userList = Arrays.asList(
        new User("Pasha","test1@bk.ru","12345", Role.ADMIN),
        new User("Ivan","test2@bk.ru","12345678", Role.USER)
);

}
