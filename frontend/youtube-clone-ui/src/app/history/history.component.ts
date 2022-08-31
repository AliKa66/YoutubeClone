import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user.service";
import {VideoService} from "../service/video.service";
import {Observable} from "rxjs";
import {VideoDto} from "../model/video-dto";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  videos: Observable<VideoDto>[] = [];
  constructor(private userService: UserService, private videoService: VideoService) {
    this.userService.getUserHistory().subscribe(response => {
      response.forEach(videoId => {
        this.videos.push(videoService.getVideo(videoId));
      })
    });
  }

  ngOnInit(): void {
  }

}
