package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class UserUpdateGenerator {
    public static UserUpdate getRandom() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru";
        String name = RandomStringUtils.randomAlphabetic(10);
        return new UserUpdate(email, name);
    }
}
