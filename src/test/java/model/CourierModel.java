package model;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierModel implements Model {
    private String login;
    private String password;
    private String firstName;

    private int id;

    public CourierModel() {
    }

    public CourierModel(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierModel(Boolean isNoLogin, String firstParam, String secondParam) {
        if (isNoLogin) {
            this.password = firstParam;
            this.firstName = secondParam;
        } else {
            this.login = firstParam;
            this.firstName = secondParam;
        }
    }

    public CourierModel(Boolean isNoLogin, String param) {
        if (isNoLogin) {
            this.password = param;
        } else {
            this.login = param;
        }
    }

    public CourierModel(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static CourierModel getRandomCourierModel() {
        String login = RandomStringUtils.randomAlphanumeric(5, 10);
        String password = RandomStringUtils.randomAlphanumeric(5, 10);
        String firstName = RandomStringUtils.randomAlphabetic(5, 10);
        return new CourierModel(login, password, firstName);
    }
}
