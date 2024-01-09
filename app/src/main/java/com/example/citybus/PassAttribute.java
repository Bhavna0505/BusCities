package com.example.citybus;

public class PassAttribute
{
    public static String Name;
    public static String Email;
    public static String Gender;

    public static void setName(String name) {
        Name = name;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static void setGender(String gender) {
        Gender = gender;
    }

    public static String getName() {
        return Name;
    }

    public static String getEmail() {
        return Email;
    }

    public static String getGender() {
        return Gender;
    }
}
