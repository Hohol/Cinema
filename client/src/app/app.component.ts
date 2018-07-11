import {Component, OnInit} from '@angular/core';
import {User} from './model/model.user';
import {NavigationStart, Router} from '@angular/router';
import {ApiService} from './services/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  currentUser: User;

  constructor(private router: Router, private api: ApiService) {
  }

  private updateCurrentUser() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  public ngOnInit() {
    this.updateCurrentUser();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.updateCurrentUser();
      }
    });
  }

  public logOut() {
    // todo real logout?
    localStorage.removeItem('currentUser');
    location.reload();
    /*this.api.logOut()
      .subscribe(r => {
        localStorage.removeItem('currentUser');
        this.router.navigate(['/']);
      });
    /**/
  }
}
