import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {UserService} from "../service/user.service";
import {CommentService} from "../service/comment.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CommentDto} from "../model/comment-dto";

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  @Input()
  videoId!: string;

  commentsForm: FormGroup;
  comments: CommentDto[] | undefined;
  constructor(private userService: UserService, private commentService: CommentService, private snackbar: MatSnackBar) {
    this.commentsForm = new FormGroup({
      comment: new FormControl('comment')
    });
  }

  ngOnInit(): void {
    this.getComments();
  }

  onPostComment() {
    const comment = this.commentsForm.get('comment')?.value;
    const commentDto: CommentDto ={
      commentText: comment,
      authorId: this.userService.userId
    }

    this.commentService.postComment(commentDto, this.videoId).subscribe(() => {
      this.snackbar.open("Comment posted successfully", "OK");
      this.commentsForm.get('comment')?.reset();
      this.getComments();
    });
  }


  getComments() {
    this.commentService.getAllComments(this.videoId).subscribe(response => {
      console.log("comments", response)
      this.comments = response;
    });
  }
}
