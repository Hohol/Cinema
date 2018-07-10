import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from '../../model/model.user';
import {AccountService} from '../../services/account.service';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  errorMessage: string;

  constructor(public accountService: AccountService, public router: Router, private authService: AuthService) {
  }

  ngOnInit() {
  }

  register() {
    this.accountService.createAccount(this.user).subscribe(data => {
        this.authService.logIn(this.user)
          .subscribe(data2 => this.router.navigate(['/']));
      }, err => {
        console.log(err);
        this.errorMessage = 'username already exist';
      }
    );
  }
}
