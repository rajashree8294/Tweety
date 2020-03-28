import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {User} from './model/user';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl = 'http://localhost:8080/tweety';
  user = new User();

  constructor(private httpClient: HttpClient) { }

  public getAllUsers(): Observable<any> {
    return this.httpClient.get(this.baseUrl + '/users');
 }

 public getUser(searchkey: string, type: string): Observable<User> {
  return this.httpClient.get<User>(this.baseUrl + '/user/' + searchkey + '/' + type);
 }

 public register(formData: any) {
   this.user.fname = formData.firstName;
   this.user.lname = formData.lastName;
   this.user.emailid = formData.email;
   this.user.password = formData.password;
   this.user.userid = '@' + formData.userid;
   this.user.role = 'user';
   return this.httpClient.post(this.baseUrl + '/register/', this.user);
 }

 public search(key: string): Observable<any> {
   return this.httpClient.get(this.baseUrl + '/search/' + key);
 }
}
