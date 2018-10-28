/*FileName : Group17_InClass06.zip
Group R-17
Assignment 6
Naga Sandhyadevi yalla, Pawan Bole , Sumanth
*/

package com.example.sandhyayalla.chatroom;

import java.io.Serializable;

public class Threaditem implements Serializable {
    String title, userid, threadid;

    @Override
    public String toString() {
        return "Threaditem{" +
                "title='" + title + '\'' +
                ", userid='" + userid + '\'' +
                ", threadid='" + threadid + '\'' +
                '}';
    }

    public Threaditem() {
    }
}
