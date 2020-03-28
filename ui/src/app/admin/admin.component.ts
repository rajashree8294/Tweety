import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { TweetService } from '../tweet.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  numTweets: number;
  numUsers: number;

  constructor(private us: UserService, private ts: TweetService) {
    this.us.getAllUsers().subscribe(data => {
      if (data) {
        this.numUsers = data.length - 1;
      }
    });

    this.ts.getAllTweets().subscribe(res => {
      if (res) {
        this.numTweets = res.length;
      }
    });
  }

  ngOnInit() {
  }



}
