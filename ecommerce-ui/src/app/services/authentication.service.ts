import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { User } from '../shared/User';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient, private router: Router) {}

  authenticate(username, password) {
    console.log(username);
    console.log(password);
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(username + ':' + password),
    });

    return this.httpClient
      .get<User>('http://localhost:8080/validateLogin', { headers })
      .pipe(
        map((res) => {
          sessionStorage.setItem('username', username);
          if (username === 'admin') {
            sessionStorage.setItem('role', 'ROLE_ADMIN');
          } else {
            sessionStorage.setItem('role', 'ROLE_USER');
          }
          let authString = 'Basic ' + btoa(username + ':' + password);
          sessionStorage.setItem('basicauth', authString);
          return res;
        })
      );
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username');
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('role');
    sessionStorage.removeItem('basicauth');
    this.router.navigate(['/home']);
  }

  getRole() {
    return sessionStorage.getItem('role');
  }
}
