package com.example.seungmin1216.team.data;

public class SaveMember {
    private static SaveMember curr = null;
    private Member member;

    public static SaveMember getInstance() {
        if (curr == null) {
            curr = new SaveMember();
        }

        return curr;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
