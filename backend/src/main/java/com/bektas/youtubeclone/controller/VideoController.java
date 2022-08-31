package com.bektas.youtubeclone.controller;

import com.bektas.youtubeclone.dto.UploadVideoResponse;
import com.bektas.youtubeclone.dto.VideoDto;
import com.bektas.youtubeclone.dto.CommentDto;
import com.bektas.youtubeclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> getVideos() {
        return service.allVideos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadVideo(@RequestParam("file") MultipartFile file) {
        return service.uploadVideo(file);
    }

    @PostMapping("thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId) {
       return service.uploadThumbnail(videoId, file);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetaData(@RequestBody VideoDto videoDto) {
        return service.editVideo(videoDto);
    }

    @GetMapping("{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto getVideoDetails(@PathVariable String videoId) {
        return service.getVideoDetails(videoId);
    }

    @PostMapping("/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto likeVideo(@PathVariable String videoId) {
        return service.likeVideo(videoId);
    }

    @PostMapping("/{videoId}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto dislikeVideo(@PathVariable String videoId) {
        return service.dislikeVideo(videoId);
    }

    @PostMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public void addComment(@PathVariable String videoId, @RequestBody CommentDto comment) {
        System.out.println(comment.getCommentText() + "-" + comment.getAuthorId());
        service.addComment(videoId, comment);
    }

    @GetMapping("{videoId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getComments(@PathVariable String videoId) {
        return service.getAllCommentsByVideoId(videoId);
    }

}
