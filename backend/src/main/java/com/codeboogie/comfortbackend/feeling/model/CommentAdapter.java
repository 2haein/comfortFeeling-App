package com.codeboogie.comfortbackend.feeling.model;

public class CommentAdapter {

    public static Comment comment(final CommentRequest commentRequest) {
        if(commentRequest == null) {
            return null;
        }
        return Comment.builder()
                .feeling_id(commentRequest.getFeeling_id())
                .userId(commentRequest.getUserId())
                .context(commentRequest.getContext())
                .publishDate(commentRequest.getPublishDate())
                .show(commentRequest.getShow())
                .build();
    }

}
