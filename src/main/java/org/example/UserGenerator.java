package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static User getRandom() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }

    public static User getRandomWithoutEmail() {
        String password = RandomStringUtils.randomAlphanumeric(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(null, password, name);
    }

    public static User getRandomWithoutPassword() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru";
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, null, name);
    }

    public static User getRandomWithoutName() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(10);
        return new User(email, password, null);
    }
}
