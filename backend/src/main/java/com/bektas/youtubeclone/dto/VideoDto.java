package com.bektas.youtubeclone.dto;

import com.bektas.youtubeclone.model.Comment;
import com.bektas.youtubeclone.model.Video;
import com.bektas.youtubeclone.model.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String id;
    private String title;
    private String description;
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private String thumbnailUrl;
    private Integer likeCount;
    private Integer dislikeCount;
    private int viewCount;

    public VideoDto(Video video) {
        id = video.getId();
        title = video.getTitle();
        description = video.getDescription();
        tags = video.getTags();
        videoUrl = video.getVideoUrl();
        videoStatus = video.getVideoStatus();
        thumbnailUrl = video.getThumbnailUrl();
        likeCount = video.getLikes().get();
        dislikeCount = video.getDisLikes().get();
        viewCount = video.getViewCount().get();
    }
}
