import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm: FormGroup;
  public user: any;
  processingStart = false;

  isUserExist = false;
  isIdTaken = false;
  isSuccess = false;
  isEmailExist = false;
  serverError = false;

  constructor(private userService: UserService, private formBuilder: FormBuilder) {
    this.createForm();
   }

  onSubmit() {
    this.processingStart = true;
    if (this.signupForm.valid) {
        const userid = '@' + this.signupForm.controls.userid.value;
        const email = this.signupForm.controls.email.value;

        this.userService.getUser(userid, 'userid').subscribe(data => {
          this.user = data;
          console.log('Inside onSubmit()' + this.user);
          if (this.user && this.user.emailid === email) {
            // user is completely present in database
            this.isUserExist = true;
            this.isIdTaken = false;
            this.isSuccess = false;
            this.isEmailExist = false;
          } else if (this.user && this.user.emailid !== email) {
            // some other user is present with the userid and with different email address
            this.isIdTaken = true;
            this.isUserExist = false;
            this.isEmailExist = false;
            this.isSuccess = false;
          } else {
            // in here, userid is unique but account is already associated with the same email id
            // tslint:disable-next-line: no-shadowed-variable
            this.userService.getUser(email, 'email').subscribe(data => {
              this.user = data;
              if (this.user && this.user.emailid === email) {
                this.isEmailExist = true;
                this.isIdTaken = false;
                this.isUserExist = false;
                this.isSuccess = false;
              } else {
                this.isIdTaken = false;
                this.isUserExist = false;
                this.isEmailExist = false;

                // tslint:disable-next-line: no-shadowed-variable
                this.userService.register(this.signupForm.value).subscribe(data => {
                  if (data) {
                    this.isSuccess = true;
                    this.isSuccess = false;
                  } else {
                    this.serverError = true;
                    this.isSuccess = false;
                  }
                });
              }
            });
          }
        });
    }
 }
 checkPasswords(group: FormGroup) {
  const pass: string = group.controls.password.value;
  const confirmPass: string = group.controls.repassword.value;
  return pass === confirmPass ? null : { notSame: true };
 }

 createForm() {
  this.signupForm = this.formBuilder.group({
    firstName: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
    lastName: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')]],
    userid: ['', [Validators.required]],
    repassword: ['', [Validators.required]]
  }, {validator: this.checkPasswords});
 }
  ngOnInit() {}
}
