import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import * as _ from 'lodash';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userList1: any[] = [];
  username: string;
  userid: string;
  key: string;
  data: User;
  isAdmin = false;

  constructor(private router: Router, private us: UserService) {
    this.data = JSON.parse(sessionStorage.getItem('User'));
    if (this.data) {
      this.username = this.data.fname;
      this.userid = this.data.userid;
      if (this.data.role === 'admin') {
        this.isAdmin = true;
      }
    } else {
      this.router.navigate(['/']);
    }
  }

  logout() {
    sessionStorage.removeItem('User');
    this.router.navigate(['/']);
  }

  ngOnInit() {
  }

  profile() {
    sessionStorage.setItem('profileUser', this.userid);
    this.router.navigate(['/profile']);
  }

  main() {
    sessionStorage.removeItem('profileUser');
    this.router.navigate(['/main']);
  }

  adminPortal() {
    sessionStorage.removeItem('profileUser');
    this.router.navigate(['/adminPortal']);
  }

  suggest() {
    if (this.key && this.key.length > 2) {
      this.userList1 = [];
      console.log(this.key);
      this.us.search(this.key).subscribe(response => {
        if (response) {
          console.log(response);
          for (const i of response) {
           this.userList1.push(_.join(i, '|'));
          }
        }
      });
    } else {
      this.userList1 = [];
    }
  }

  search() {
    const k = _.split(this.key, '|')[0];
    console.log('Userid Fetched to Search: ' + k);
    sessionStorage.setItem('profileUser', k);
    this.router.navigate(['/profile']);
  }
}
