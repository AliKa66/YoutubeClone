package com.bektas.youtubeclone.service;

import com.bektas.youtubeclone.dto.UploadVideoResponse;
import com.bektas.youtubeclone.dto.VideoDto;
import com.bektas.youtubeclone.dto.CommentDto;
import com.bektas.youtubeclone.model.Comment;
import com.bektas.youtubeclone.model.Video;
import com.bektas.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;

    public UploadVideoResponse uploadVideo(MultipartFile file) {
        String videoUrl = s3Service.uploadFile(file);
        Video video = new Video();
        video.setVideoUrl(videoUrl);

        Video savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        Video video = getVideoById(videoDto.getId());

        video.setTitle(videoDto.getTitle());
        video.setDescription(videoDto.getDescription());
        video.setTags(videoDto.getTags());
        video.setThumbnailUrl(videoDto.getThumbnailUrl());
        video.setVideoStatus(videoDto.getVideoStatus());

        videoRepository.save(video);
        return videoDto;
    }

    public String uploadThumbnail(String videoId, MultipartFile file) {
        Video video = getVideoById(videoId);
        String thumbnailUrl = s3Service.uploadFile(file);
        video.setThumbnailUrl(thumbnailUrl);

        videoRepository.save(video);
        return thumbnailUrl;
    }

    public VideoDto getVideoDetails(String videoId) {
        Video video = getVideoById(videoId);
        increaseVideoCount(video);
        userService.addVideoToHistory(video.getId());
        VideoDto videoDto = new VideoDto(video);
        return videoDto;
    }

    public VideoDto likeVideo(String videoId) {
        Video video = getVideoById(videoId);

        if (userService.ifLikedVideo(videoId)){
            video.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if(userService.ifDislikedVideo(videoId)) {
            video.decrementDislikes();
            userService.removeFromDislikedVideos(videoId);
            video.incrementLikes();
            userService.addToLikedVideos(videoId);
        } else {
            video.incrementLikes();
            userService.addToLikedVideos(videoId);
        }
        videoRepository.save(video);
        VideoDto videoDto = new VideoDto(video);
        return videoDto;
    }

    public VideoDto dislikeVideo(String videoId) {
        Video video = getVideoById(videoId);

        if (userService.ifDislikedVideo(videoId)){
            video.decrementDislikes();
            userService.removeFromDislikedVideos(videoId);
        } else if(userService.ifLikedVideo(videoId)) {
            video.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            video.incrementDislikes();
            userService.addToDislikedVideos(videoId);
        } else {
            video.incrementDislikes();
            userService.addToDislikedVideos(videoId);
        }
        videoRepository.save(video);
        VideoDto videoDto = new VideoDto(video);
        return videoDto;
    }

    public void addComment(String videoId, CommentDto comment) {
        Video video = getVideoById(videoId);

        Comment fromDto = CommentDto.toComment(comment);

        video.addComment(fromDto);
        videoRepository.save(video);
    }

    public List<CommentDto> getAllCommentsByVideoId(String videoId) {
        Video video = getVideoById(videoId);

        return video
                .getCommentList()
                .stream()
                .map(this::mapToCommentDto)
                .collect(Collectors.toList());
    }

    public List<VideoDto> allVideos() {
        List<Video> videos = videoRepository.findAll();
        return videos
                .stream()
                .map(this::mapToVideoDto)
                .collect(Collectors.toList());
    }

    private VideoDto mapToVideoDto(Video video) {
        VideoDto videoDto = new VideoDto(video);
        return videoDto;
    }

    private Video getVideoById(String id) {
        return videoRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id - " + id));
    }

    private void increaseVideoCount(Video video) {
        video.incrementViewCount();
        videoRepository.save(video);
    }

    private CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorId(comment.getAuthorId());
        commentDto.setCommentText(comment.getText());
        return commentDto;
    }
}
