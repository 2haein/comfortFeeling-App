package com.codeboogie.comfortbackend.model;


public class FeelingAdapter {

    public static Feeling feeling(final FeelingRequest feelingRequest) {
        if(feelingRequest == null) {
            return null;
        }
        return Feeling.builder()
                .userId(feelingRequest.getUserId())
                .score(feelingRequest.getScore())
                .publishDate(feelingRequest.getPublishDate())
                .text(feelingRequest.getText())
                .xcoord(feelingRequest.getXcoord())
                .ycoord(feelingRequest.getYcoord())
                .build();
    }

}
