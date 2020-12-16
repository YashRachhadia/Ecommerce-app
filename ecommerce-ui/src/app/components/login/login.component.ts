import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {}

  loginForm: FormGroup;
  errorMsg: any;
  invalidLogin: boolean = false;

  public get username() {
    return this.loginForm.get('username');
  }

  public get password() {
    return this.loginForm.get('password');
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  login() {
    if (this.loginForm.valid) {
      this.authenticationService
        .authenticate(this.username.value, this.password.value)
        .subscribe(
          (res) => {
            console.log(res);
            this.router.navigate(['/productList']);
            if (this.invalidLogin) {
              this.invalidLogin = !this.invalidLogin;
            }
          },
          (error) => {
            console.log(error);
            this.errorMsg = 'Invalid Login Credentials';
            if (!this.invalidLogin) {
              this.invalidLogin = !this.invalidLogin;
            }
          }
        );
    }
  }
}
