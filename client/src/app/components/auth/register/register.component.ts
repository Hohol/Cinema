import {Component, ViewEncapsulation} from '@angular/core';
import {User} from '../../../model/model.user';
import {Router} from '@angular/router';
import {Moment} from 'moment';
import {ApiService} from '../../../services/api.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent {
  user: User = new User();
  birthday: Moment;
  errorMessage: string;

  constructor(private api: ApiService, private router: Router) {
  }

  register() {
    this.user.birthday = this.birthday.format('YYYY-MM-DD');
    this.api.createAccount(this.user).subscribe(response => {
        this.api.logIn(this.user)
          .subscribe(response2 => this.router.navigate(['/']));
      }, err => {
        this.errorMessage = 'username already exist';
      }
    );
  }
}
