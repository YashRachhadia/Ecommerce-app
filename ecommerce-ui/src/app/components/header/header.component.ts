import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  constructor(
    private router: Router,
    public authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {}

  logout() {
    this.authenticationService.logOut();
    this.router.navigate['/login'];
  }

  get loggedInUsername() {
    return sessionStorage.getItem('username');
  }
}
