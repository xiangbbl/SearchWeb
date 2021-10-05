package com.example.community.enums;


public enum CommentType {
    COMMENT_lvl1(1),
    COMMENT_lvl2(2);
    private Integer type;


    public Integer getType() {
        return type;
    }

    CommentType(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentType commentType : CommentType.values()) {
            if (commentType.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
