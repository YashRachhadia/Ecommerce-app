import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  constructor(private fb: FormBuilder, private userService: UserService) {}

  registrationForm: FormGroup;
  success: boolean = false;
  error: boolean = false;
  public get customerName() {
    return this.registrationForm.get('customerName');
  }

  public get username() {
    return this.registrationForm.get('username');
  }

  public get customerEmail() {
    return this.registrationForm.get('customerEmail');
  }

  public get customerAddress() {
    return this.registrationForm.get('customerAddress');
  }

  public get password() {
    return this.registrationForm.get('password');
  }

  public get confirmPassword() {
    return this.registrationForm.get('confirmPassword');
  }

  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      customerName: ['', Validators.required],
      username: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(15),
        ],
      ],
      customerEmail: [
        '',
        [
          Validators.required,
          Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$'),
        ],
      ],
      customerAddress: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  save() {
    this.userService.addCustomers(this.registrationForm.value).subscribe(
      (data) => {
        console.log('Success!', data);
        if (!this.success) {
          this.success = !this.success;
        }
        this.registrationForm.reset();
      },
      (error) => console.log('Error', error)
    );
  }
  register() {
    if (this.registrationForm.valid) {
      if (
        this.registrationForm.get('password').value !==
        this.registrationForm.get('confirmPassword').value
      ) {
        if (this.success) {
          this.success = !this.success;
        }
        this.error = !this.error;
      } else {
        if (this.error) {
          this.error = !this.error;
        }
        this.save();
      }
    } else {
      if (this.success) {
        this.success = !this.success;
      }
    }
  }
}
