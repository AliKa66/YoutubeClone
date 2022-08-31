import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../service/video.service";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css']
})
export class VideoDetailComponent implements OnInit {
  videoId: string;
  videoUrl!: string;
  videoTitle!: string;
  videoDescription!: string;
  tags!: string[];
  likeCount: number = 0;
  dislikeCount: number = 0;
  viewCount: number = 0;
  showSubscribeButton = true;

  constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService, private userService: UserService) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    this.videoService.getVideo(this.videoId).subscribe(response => {
      this.videoUrl = response.videoUrl;
      this.videoTitle = response.title;
      this.videoDescription = response.description;
      this.tags = response.tags;
      this.likeCount = response.likeCount;
      this.dislikeCount = response.dislikeCount;
      this.viewCount = response.viewCount;
    });
  }

  ngOnInit(): void {
  }

  onLike() {
    this.videoService.likeVideo(this.videoId).subscribe(response => {
      this.likeCount = response.likeCount;
      this.dislikeCount = response.dislikeCount;
    });
  }

  onDislike() {
    this.videoService.dislikeVideo(this.videoId).subscribe(response => {
      this.likeCount = response.likeCount;
      this.dislikeCount = response.dislikeCount;
    });
  }

  onSubscribe() {
    const userId = this.userService.userId;
    this.userService.subscribeToUser(userId).subscribe(response => {
      this.showSubscribeButton = false;
    });
  }

  onUnsubscribe() {
    const userId = this.userService.userId;
    this.userService.unsubscribeToUser(userId).subscribe(response => {
      this.showSubscribeButton = true;
    });
  }
}
