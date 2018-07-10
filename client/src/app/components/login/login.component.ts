import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from '../../model/model.user';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent {
  user: User = new User();
  errorMessage: string;

  constructor(private authService: AuthService, private router: Router) {
  }

  login() {
    this.authService.logIn(this.user)
      .subscribe(response => {
          this.router.navigate(['/']);
        }, err => {
          this.errorMessage = 'error :  Username or password is incorrect';
        }
      );
  }
}
