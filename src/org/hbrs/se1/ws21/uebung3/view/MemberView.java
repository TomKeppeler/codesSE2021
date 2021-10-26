package org.hbrs.se1.ws21.uebung3.view;

import java.util.List;

import org.hbrs.se1.ws21.uebung3.controll.Member;

public class MemberView{
    public static void dump(List<Member> list){
        for (Member member : list) {
            System.out.println(member.toString());
        }
    }
}