import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  isUserNotExist = false;
  isSuccess = false;
  processingStart = false;
  user: any;

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) {
    this.createForm();
  }

  ngOnInit() {
  }

  onSubmit() {
    this.processingStart = true;
    if (this.loginForm.valid) {
      const email = this.loginForm.controls.email.value;
      const password = this.loginForm.controls.password.value;

      this.userService.getUser(email, 'email').subscribe(data => {
        this.user = data;
        if (this.user && this.user.password === password) {
          this.isUserNotExist = false;
          this.isSuccess = true;
          sessionStorage.setItem('User', JSON.stringify(this.user));
          if (this.user.role === 'admin') {
            this.router.navigate(['/adminPortal']);
          } else {
            this.router.navigate(['/main']);
          }
        } else {
          this.isUserNotExist = true;
          this.isSuccess = false;
        }
      });
    }
  }

  createForm() {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
   }

}
