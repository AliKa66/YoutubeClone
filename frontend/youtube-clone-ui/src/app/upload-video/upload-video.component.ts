import { Component, OnInit } from '@angular/core';
import {NgxFileDropEntry} from "ngx-file-drop";
import {VideoService} from "../service/video.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.css']
})
export class UploadVideoComponent implements OnInit {

  private fileEntry: FileSystemFileEntry | undefined;
  public files: NgxFileDropEntry[] = [];
  public fileUploaded = false;

  constructor(private videoService: VideoService, private router: Router) {
  }

  ngOnInit(): void {
  }

  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {

      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.fileEntry.file((file: File) => {
          this.fileUploaded = true;
        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event: any){
    console.log(event);
  }

  public fileLeave(event: any){
    console.log(event);
  }

  public uploadVideo() {
    if (this.fileEntry !== undefined) {
      this.fileEntry.file(file => {
        this.videoService.uploadVideo(file).subscribe((response) => {
          this.router.navigateByUrl("/save-video-details/" + response.videoId);
        })
      });
    }
  }

}
