import { Component, OnInit } from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Router} from "@angular/router";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isAuthenticated: boolean = false;

  constructor(private oidcSecurityService: OidcSecurityService, private router: Router) { }

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated}) => {
      this.isAuthenticated = isAuthenticated;
    });
  }

  login() {
    this.oidcSecurityService.authorize();
  }

  logout() {
    this.oidcSecurityService.logoffAndRevokeTokens();
    this.oidcSecurityService.logoffLocal();
    this.router.navigateByUrl('');
  }
}
