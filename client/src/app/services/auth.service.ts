import {Injectable} from '@angular/core';
import {User} from '../model/model.user';
import {map} from 'rxjs/operators';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ApiService} from './api.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  public logIn(user: User) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Authorization': 'Basic ' + btoa(user.username + ':' + user.password)
      })
    };
    return this.http.get(ApiService.API_URL + '/account/login', httpOptions)
      .pipe(map(response => {
        const responseUser = response['principal'];
        console.log('ru = ' + responseUser);
        if (responseUser) {
          console.log(responseUser);
          localStorage.setItem('currentUser', JSON.stringify(responseUser));
        }
      }));
  }

  logOut() {
    return this.http.post(ApiService.API_URL + '/logout', {});
  }

  createAccount(user: User) {
    return this.http.post(ApiService.API_URL + '/account/register', user);
  }
}
