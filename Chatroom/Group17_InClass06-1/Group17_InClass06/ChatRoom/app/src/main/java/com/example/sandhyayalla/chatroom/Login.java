/*FileName : Group17_InClass06.zip
Group R-17
Assignment 6
Naga Sandhyadevi yalla, Pawan Bole , Sumanth
*/

package com.example.sandhyayalla.chatroom;

public class Login {
    String username;
    String password;

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
