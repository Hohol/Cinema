import {Component, OnInit} from '@angular/core';
import {User} from './model/model.user';
import {NavigationStart, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  static API_URL = 'http://localhost:8090';
  currentUser: User;

  constructor(private router: Router) {
    this.updateCurrentUser();
  }

  private updateCurrentUser() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  public ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.updateCurrentUser();
      }
    });
  }
}
