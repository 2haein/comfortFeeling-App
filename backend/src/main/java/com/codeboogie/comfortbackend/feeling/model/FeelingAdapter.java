package com.codeboogie.comfortbackend.feeling.model;


public class FeelingAdapter {

    public static Feeling feeling(final FeelingRequest feelingRequest) {
        if(feelingRequest == null) {
            return null;
        }
        return Feeling.builder()
                .userId(feelingRequest.getUserId())
                .score(feelingRequest.getScore())
                .publishDate(feelingRequest.getPublishDate())
                .updateDate(feelingRequest.getUpdateDate())
                .text(feelingRequest.getText())
                .xcoord(feelingRequest.getXcoord())
                .ycoord(feelingRequest.getYcoord())
                .build();
    }

}
