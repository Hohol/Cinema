import {Component, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../../model/model.user';
import {ApiService} from '../../../services/api.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent {
  user: User = new User();
  errorMessage: string;

  constructor(private api: ApiService, private router: Router) {
  }

  login() {
    this.api.logIn(this.user)
      .subscribe(response => {
          this.router.navigate(['/']);
        }, err => {
          this.errorMessage = 'error :  Username or password is incorrect';
        }
      );
  }
}
