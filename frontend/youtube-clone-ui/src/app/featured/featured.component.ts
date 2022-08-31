import { Component, OnInit } from '@angular/core';
import {VideoService} from "../service/video.service";
import {VideoDto} from "../model/video-dto";

@Component({
  selector: 'app-featured',
  templateUrl: './featured.component.html',
  styleUrls: ['./featured.component.css']
})
export class FeaturedComponent implements OnInit {
  videos!: VideoDto[];

  constructor(private videoService: VideoService) {
    this.videoService.getAllVideos().subscribe(response => {
      this.videos = response;
    });
  }

  ngOnInit(): void {
  }

}
