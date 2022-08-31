import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {MatChipInputEvent} from "@angular/material/chips";
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../service/video.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {VideoDto} from "../model/video-dto";

@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrls: ['./save-video-details.component.css']
})
export class SaveVideoDetailsComponent implements OnInit {

  private videoId: string;
  private thumbnailUrl!: string;
  title: FormControl;
  description: FormControl;
  videoStatus: FormControl;
  saveVideoDetailsForm: FormGroup;
  addOnBlur = true;
  tags: string[] = [];
  selectedThumbnail!: File;
  fileSelected = false;

  videoUrl!: string;
  selectedThumbnailName: string = '';
  readonly separatorKeysCodes = [ENTER, COMMA] as const;


  constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService, private snackBar: MatSnackBar) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    this.videoService.getVideo(this.videoId).subscribe(response => {
      this.videoUrl = response.videoUrl;
      this.thumbnailUrl = response.thumbnailUrl;
    });
    this.title = new FormControl('');
    this.description = new FormControl('');
    this.videoStatus = new FormControl('');
    this.saveVideoDetailsForm = new FormGroup({
      title: this.title,
      description: this.description,
      videoStatus: this.videoStatus
    });
  }

  ngOnInit(): void {
  }


  add(event: MatChipInputEvent): void {
    const tag = (event.value || '').trim();

    if (tag) {
      this.tags.push(tag);
    }

    event.chipInput!.clear();
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  onFileSelected($event: Event) {
    // @ts-ignore
    this.selectedThumbnail = $event.target.files[0];
    this.selectedThumbnailName = this.selectedThumbnail.name;
    this.fileSelected = true;
  }

  onThumbnailUpload() {
    this.videoService.uploadThumbnail(this.videoId, this.selectedThumbnail).subscribe(response => {
      this.thumbnailUrl = response;
      this.snackBar.open("Thumbnail upload successful", "OK");
    });
  }

  saveVideo() {
    const videoMetadata: VideoDto = {
      id: this.videoId,
      title: this.saveVideoDetailsForm.get('title')?.value,
      description: this.saveVideoDetailsForm.get('descripiton')?.value,
      tags: this.tags,
      videoStatus: this.saveVideoDetailsForm.get('videoStatus')?.value,
      videoUrl: this.videoUrl,
      thumbnailUrl: this.thumbnailUrl,
      likeCount: 0,
      dislikeCount: 0,
      viewCount: 0
    }
    this.videoService.saveVideo(videoMetadata).subscribe(response => {
      this.snackBar.open("Video metadata updated successfully", "OK")
    });
  }
}
