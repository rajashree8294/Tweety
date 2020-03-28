import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { FollowService } from '../follow.service';
import { User } from '../model/user';
import * as _ from 'lodash';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  showBtn = false;
  loggedIn: any;
  loggedInUserid: string;
  userid: string;
  username: string;
  tweets: number;
  following: number;
  followings: any[] = [];
  followers: number;
  user: User;
  btnText: string;
  constructor(private us: UserService, private fs: FollowService) {
    this.userid = sessionStorage.getItem('profileUser');
    this.loggedIn = JSON.parse(sessionStorage.getItem('User'));
    if (this.userid) {
        this.loggedInUserid = this.loggedIn.userid;
        this.getFollowingInfo();
        this.getFollowerInfo();

        if (this.userid === this.loggedInUserid) {
          this.showBtn = false;
          this.username = this.loggedIn.fname + ' ' + this.loggedIn.lname;
          this.tweets = this.loggedIn.tweets.length;
        } else {
          this.showBtn = true;
          this.getLoggedInFollowingInfo();
        }
        this.loadUserInfo();
    }
  }

  ngOnInit() {
  }

  loadUserInfo() {
    this.us.getUser(this.userid, 'userid').subscribe(response => {
      console.log(response);
      if (response) {
        this.username = response.fname + ' ' + response.lname;
        this.tweets = response.tweets.length;
      }
    });
  }

  getFollowingInfo() {
    this.fs.getFollowing(this.userid).subscribe(data => {
      if (data) {
        this.following = data.length;
      }
    });
  }

  getFollowerInfo() {
    this.fs.getFollowers(this.userid).subscribe(resp => {
      console.log(resp);
      this.followers = resp.length;
    });
  }

  getLoggedInFollowingInfo() {
    this.fs.getFollowing(this.loggedInUserid).subscribe(resp => {
      if (resp.length > 0) {
        for (const r in resp) {
          if (resp[r] === this.userid) {
            this.btnText = 'Following';
            break;
          } else {
            this.btnText = 'Follow';
          }
        }
      } else {
        this.btnText = 'Follow';
      }
    });
  }

  action() {
    if (this.btnText === 'Follow') {
      this.fs.follow(this.loggedInUserid, this.userid).subscribe(resp => {
        if (resp) {
          this.btnText = 'Following';
        }
      });
    } else {
      this.fs.getFollowId(this.loggedInUserid, this.userid).subscribe(id => {
        if (id > 0) {
          this.fs.unfollow(id).subscribe(result => {
            if (result >= 0) {
              this.btnText = 'Follow';
            } else {
              window.alert('Server Side Error while Unfollowing the user');
            }
          });
        } else {
          this.btnText = 'Follow';
        }
      });
    }
  }
}
