/*FileName : GroupR17_HW6.zip
Group R-17
Assignment 6
Naga Sandhyadevi yalla, Pawan Bole , Sumanth
*/

package com.example.sandhyayalla.chatroom;

import java.io.Serializable;

public class Threaditem implements Serializable {
    String fname,lname,title, userid, threadid;

    @Override
    public String toString() {
        return "Threaditem{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", title='" + title + '\'' +
                ", userid='" + userid + '\'' +
                ", threadid='" + threadid + '\'' +
                '}';
    }

    public Threaditem() {
    }
}
