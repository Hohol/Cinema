import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {User} from '../model/model.user';
import {UserStats} from '../model/model.user-stats';
import {BaseApiService} from './base-api.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService extends BaseApiService {

  constructor(http: HttpClient) {
    super(http);
  }

  getUsers(): Observable<User[]> {
    return this.get<User[]>('/users');
  }

  getUserStats(userId: number): Observable<UserStats> {
    return this.get<UserStats>(`/user/${userId}`);
  }

  logIn(user: User) {
    return this.http.get(ApiService.API_URL + '/account/login', this.getUserAuthHeaders(user))
      .pipe(map(response => {
        const responseUser = response['principal'];
        if (responseUser) {
          localStorage.setItem('currentUser', JSON.stringify(responseUser));
        }
      }));
  }

  createAccount(user: User) {
    return this.http.post(ApiService.API_URL + '/account/register', user);
  }
}
