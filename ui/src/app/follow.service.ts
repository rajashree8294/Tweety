import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FollowService {
  baseUrl = 'http://localhost:8080/tweety';

  constructor(private httpClient: HttpClient) { }

  public getFollowers(userid: string): Observable<any> {
    return this.httpClient.get(this.baseUrl + '/followers/' + userid);
  }

  public getFollowing(userid: string): Observable<any> {
    return this.httpClient.get(this.baseUrl + '/following/' + userid);
  }

  public follow(follower: string, following: string): Observable<any> {
    const postbody = {
      "follower_userid": follower,
      "following_userid": following
    };

    return this.httpClient.post(this.baseUrl + '/follow', postbody);
  }

  public unfollow(followid: number): Observable<any> {
    return this.httpClient.delete(this.baseUrl + '/unfollow/' + followid);
  }

  public getFollowId(follower: string, following: string): Observable<any> {
    return this.httpClient.get(this.baseUrl + '/followId/' + follower + '/' + following);
  }

}
