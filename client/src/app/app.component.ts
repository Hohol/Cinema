import {Component, OnInit} from '@angular/core';
import {User} from './model/model.user';
import {NavigationStart, Router} from '@angular/router';
import {AuthService} from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  static API_URL = 'http://localhost:8090';
  currentUser: User;

  constructor(private router: Router, private auth: AuthService) {
  }

  private updateCurrentUser() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  public ngOnInit() {
    this.updateCurrentUser();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.currentUser = {username: 'asdf', password: 'asdf', id: 5};
        console.log(this.currentUser);
        this.updateCurrentUser();
      }
    });
  }

  public logout() {
    this.auth.logOut()
      .subscribe(r => {
        localStorage.removeItem('currentUser');
        this.router.navigate(['/']);
      });
  }
}
