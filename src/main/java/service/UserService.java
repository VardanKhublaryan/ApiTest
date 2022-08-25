package service;

import static service.Configuration.USERS;

public class UserService extends BaseService{


    public static String getUsers(){
       return Get(USERS).asString();
    }
}
