import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions, Response} from '@angular/http';
import {User} from '../model/model.user';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  API_URL = 'http://localhost:8090'; // todo get rid of duplication

  constructor(public http: Http) {
  }

  public logIn(user: User) {
    const headers = new Headers();
    headers.append('Accept', 'application/json');
    const base64Credential: string = btoa(user.username + ':' + user.password);
    headers.append('Authorization', 'Basic ' + base64Credential);

    const options = new RequestOptions();
    options.headers = headers;

    return this.http.get(this.API_URL + '/account/login', options)
      .pipe(map((response: Response) => {
        const responseUser = response.json().principal;
        if (responseUser) {
          localStorage.setItem('currentUser', JSON.stringify(responseUser));
        }
      }));
  }

  logOut(): Observable<Response> {
    return this.http.post(this.API_URL + '/logout', {});
  }
}
