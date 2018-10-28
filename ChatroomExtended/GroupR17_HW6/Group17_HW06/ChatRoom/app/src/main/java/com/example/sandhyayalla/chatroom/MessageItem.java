/*FileName : GroupR17_HW6.zip
Group R-17
Assignment 6
Naga Sandhyadevi yalla, Pawan Bole , Sumanth
*/

package com.example.sandhyayalla.chatroom;

public class MessageItem {
    String fname,lname,userid,messageid,message,createdtime;

    @Override
    public String toString() {
        return "MessageItem{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", userid='" + userid + '\'' +
                ", messageid='" + messageid + '\'' +
                ", message='" + message + '\'' +
                ", createdtime='" + createdtime + '\'' +
                '}';
    }

    public MessageItem()
    {}
}
