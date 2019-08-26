package com.pumpit.app.pumpit;

public class MemberMetadata {
    String activemembcount, allmembcount, overduemembcount;
    public MemberMetadata(){

    }

    public MemberMetadata(String activemembcount, String allmembcount, String overduemembcount) {
        this.activemembcount = activemembcount;
        this.allmembcount = allmembcount;
        this.overduemembcount = overduemembcount;
    }

    public String getActivemembcount() {
        return activemembcount;
    }

    public String getAllmembcount() {
        return allmembcount;
    }

    public String getOverduemembcount() {
        return overduemembcount;
    }
}
