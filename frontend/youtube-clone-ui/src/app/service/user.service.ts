import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _userId: string = '';

  constructor(private httpClient: HttpClient) {
  }

  get userId(): string {
    return this._userId;
  }

  subscribeToUser(userId: string): Observable<boolean> {
    return this.httpClient.post<boolean>("http://localhost:8080/api/user/subscribe/" + userId, null);
  }

  registerUser(): void {
    this.httpClient.get("http://localhost:8080/api/user/register", {responseType: "text"}).subscribe(response => {
      this._userId = response;
      console.log("registered ", response, this._userId);
    });
  }

  unsubscribeToUser(userId: string): Observable<boolean> {
    return this.httpClient.post<boolean>("http://localhost:8080/api/user/unsubscribe/" + userId, null);
  }

  getUserHistory(): Observable<string[]> {
    return this.httpClient.get<string[]>("http://localhost:8080/api/user/" + this.userId + '/history');
  }
}
