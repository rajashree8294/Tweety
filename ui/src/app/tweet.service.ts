import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TweetService {
  baseUrl = 'http://localhost:8080/tweety';

  constructor(private httpClient: HttpClient) { }

  public postTweet(tweet: string, userid: string): Observable<any> {
    const postbody = {'tweet': tweet};
    return this.httpClient.put(this.baseUrl + '/addTweet/' + userid, postbody);
  }

  public getConnectionTweets(userid: string): Observable<any> {
    return this.httpClient.get(this.baseUrl + '/connectionTweets/' + userid);
  }

  public getFollowingTweets(userid: string): Observable<any> {
    return this.httpClient.get(this.baseUrl + '/followingTweets/' + userid);
  }

  public getAllTweets(): Observable<any> {
    return this.httpClient.get(this.baseUrl + '/tweets');
  }

  public removeTweet(tweetid: string) {
    return this.httpClient.delete(this.baseUrl + '/removeTweet/' + tweetid);
  }
}
