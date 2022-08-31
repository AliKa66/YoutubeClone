package com.bektas.youtubeclone.service;

import com.bektas.youtubeclone.model.User;
import com.bektas.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getCurrentUser() {
        String sub = ((Jwt) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getClaim("sub");

        return repository
                .findBySub(sub)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with sub - " + sub));
    }

    public void addToLikedVideos(String videoId) {
        User user = getCurrentUser();
        user.addToLikedVideos(videoId);
        repository.save(user);
    }

    public void removeFromLikedVideos(String videoId) {
        User user = getCurrentUser();
        user.removeFromLikedVideos(videoId);
        repository.save(user);
    }

    public void removeFromDislikedVideos(String videoId) {
        User user = getCurrentUser();
        user.removeFromDislikedVideos(videoId);
        repository.save(user);
    }

    public void addToDislikedVideos(String videoId) {
        User user = getCurrentUser();
        user.addToDislikedVideos(videoId);
        repository.save(user);
    }

    public boolean ifLikedVideo(String videoId) {
       return getCurrentUser()
                .getLikedVideos()
                .stream()
                .anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public boolean ifDislikedVideo(String videoId) {
       return getCurrentUser()
                .getDislikedVideos()
                .stream()
                .anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public void addVideoToHistory(String videoId) {
        User user = getCurrentUser();
        user.addToVideoHistory(videoId);
        repository.save(user);
    }

    public void subscribeToUser(String userId) {
        User user = getCurrentUser();
        user.subscribeToUser(userId);
        User subscribedUser = getUserById(userId);
        subscribedUser.addSubscriber(user.getId());

        repository.save(user);
        repository.save(subscribedUser);
    }
    public void unsubscribeToUser(String userId) {
        User user = getCurrentUser();
        user.unsubscribeFromUser(userId);
        User subscribedUser = getUserById(userId);
        subscribedUser.removeSubscriber(user.getId());

        repository.save(user);
        repository.save(subscribedUser);
    }

    public Set<String> userHistory(String userId) {
        User user = getUserById(userId);
        return user.getVideoHistory();
    }

    private User getUserById(String userId) {
        return repository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with user id " + userId));
    }
}
