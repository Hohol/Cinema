import {Component, ViewEncapsulation} from '@angular/core';
import {User} from '../../model/model.user';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {Moment} from 'moment';

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

  constructor(private auth: AuthService, private router: Router) {
  }

  register() {
    this.user.birthday = this.birthday.format('YYYY-MM-DD');
    this.auth.createAccount(this.user).subscribe(response => {
        this.auth.logIn(this.user)
          .subscribe(response2 => this.router.navigate(['/']));
      }, err => {
        console.log(err);
        this.errorMessage = 'username already exist';
      }
    );
  }
}
