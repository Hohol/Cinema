import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions, Response} from '@angular/http';
import {User} from '../model/model.user';
import {AppComponent} from '../app.component';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(public http: Http) {
  }

  public logIn(user: User) {

    const headers = new Headers();
    headers.append('Accept', 'application/json');
    // creating base64 encoded String from user name and password
    const base64Credential: string = btoa(user.username + ':' + user.password);
    headers.append('Authorization', 'Basic ' + base64Credential);

    const options = new RequestOptions();
    options.headers = headers;

    return this.http.get(AppComponent.API_URL + '/account/login', options)
      .pipe(map((response: Response) => {
        // login successful if there's a jwt token in the response
        const responseUser = response.json().principal;
        if (responseUser) {
          // store user details  in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(responseUser));
        }
      }));
  }

  logOut() {
    return this.http.post(AppComponent.API_URL + '/logout', {})
      .subscribe((response: Response) => {
        localStorage.removeItem('currentUser');
      });
  }
}
