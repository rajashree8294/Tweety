import { Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator, MatTableDataSource, MatDialog} from '@angular/material';
import { Router } from '@angular/router';
import { FollowService } from '../follow.service';
import { TweetService } from '../tweet.service';
import {trigger, state, style, animate, transition } from '@angular/animations';

import * as _ from 'lodash';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class MainComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;

  userid: string;
  followerNum = 0;
  followingNum = 0;
  tweet: string;

  follower: [];
  following: [];
  fTweet: [];
  cTweet: [];

  elementData: Tweets[] = [];
  elementData1: MyTweets[] = [];
  contactsTweetId: any;
  displayedColumns: string[] = ['userid', 'tweet'];
  displayedColumns1: string[] = ['userid', 'tweet', 'delete'];
  dataSource: MatTableDataSource<Tweets>;
  dataSource1: MatTableDataSource<MyTweets>;

  constructor(private router: Router, private fs: FollowService, private ts: TweetService) {
    if (!JSON.parse(sessionStorage.getItem('User'))) {
      this.router.navigate(['/']);
    }
    this.userid = JSON.parse(sessionStorage.getItem('User')).userid;

   // this.connectionsTweet(this.userid);
    this.followers();
    this.followings();

    this.followingsTweet(this.userid);
  }

  ngOnInit() {
  }

  public followers() {
    this.fs.getFollowers(this.userid).subscribe(response => {
      console.log('followers: ' + response);
      this.follower = response;
      this.followerNum = this.follower.length;
    });
  }

  public followings() {
    this.fs.getFollowing(this.userid).subscribe(response => {
      console.log('followings: ' + response);
      this.following = response;
      this.followingNum = this.following.length;
    });
  }

  public postTweet() {
    this.ts.postTweet(this.tweet, this.userid).subscribe(response => {
      if (response) {
        console.log('Success!!');
        this.tweet = '';
        this.getAllTweets();
      } else {
        console.log('Error');
      }
    });
  }

  followingsTweet(userid: string): any {
    this.ts.getFollowingTweets(userid).subscribe(response => {
      if (response) {
        this.fTweet = response;
        console.log('Followings\' Tweet IDs: ' + response);
        this.getAllTweets();
      }
    });
  }

  getAllTweets() {
    this.elementData = [];
    this.elementData1 = [];
    const fcTweets = _.uniq(this.fTweet);
   // if (fcTweets.length > 0 || ) {
    this.ts.getAllTweets().subscribe(response => {
      if (response.length > 0) {
        for (const t of response) {
          if (_.indexOf(fcTweets, t['tweetid']) >= 0 || t['userid'] === this.userid) {
            this.elementData.push({"userid": t['userid'], "tweet": t['tweet']});
            if (t['userid'] === this.userid) {
              this.elementData1.push({"userid": t['userid'], "tweet": t['tweet'], "delete": "Delete", "tweetid": t['tweetid']});
            }
          }
        }
        this.dataSource = new MatTableDataSource<Tweets>(this.elementData);
        this.dataSource.paginator = this.paginator;
        this.dataSource1 = new MatTableDataSource<MyTweets>(this.elementData1);
      }
    });
   // }
  }

  selectRow(row: any) {
    if (confirm('Are you sure you want to delete the tweet?')) {
      console.log(row);
      this.ts.removeTweet(row['tweetid']).subscribe(response => {
        if (response) {
          this.getAllTweets();
        }
      }, (err) => {
        console.log('Server side error while deleting the tweet');
      });
    } else {
      console.log('You pressed Cancel!');
    }
  }
}

export interface Tweets {
  userid: string;
  tweet: string;
}

export interface MyTweets {
  userid: string;
  tweet: string;
  delete: string;
  tweetid: number;
}
