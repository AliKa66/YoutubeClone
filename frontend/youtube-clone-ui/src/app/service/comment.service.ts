import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CommentDto} from "../model/comment-dto";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) {
  }

  postComment(commentDto: CommentDto, videoId: string): Observable<any> {
    console.log(commentDto, videoId)
    return this.httpClient.post("http://localhost:8080/api/videos/" + videoId + '/comment', commentDto);
  }

  getAllComments(videoId: string): Observable<CommentDto[]> {
    console.log(videoId)
    return this.httpClient.get<CommentDto[]>("http://localhost:8080/api/videos/" + videoId + '/comments');
  }
}
