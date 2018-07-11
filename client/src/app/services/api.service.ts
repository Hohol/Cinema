import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Movie} from '../model/model.movie';
import {Observable} from 'rxjs';
import {Seance} from '../model/model.seance';
import {map} from 'rxjs/operators';
import {User} from '../model/model.user';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  static API_URL = 'http://localhost:8090';

  constructor(private http: HttpClient) {
  }

  getMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(ApiService.API_URL + '/movies', this.getAuthHeaders());
  }

  getSeances() {
    return this.http.get<Seance[]>(ApiService.API_URL + '/seances', this.getAuthHeaders());
  }

  createMovie(movie: Movie) {
    return this.http.post<Seance[]>(ApiService.API_URL + '/movies/create', movie, this.getAuthHeaders());
  }

  logIn(user: User) {
    return this.http.get(ApiService.API_URL + '/account/login', this.getUserAuthHeaders(user))
      .pipe(map(response => {
        const responseUser = response['principal'];
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

  private getAuthHeaders() {
    const user = JSON.parse(localStorage.getItem('currentUser'));
    if (!user) {
      return {};
    }
    return this.getUserAuthHeaders(user);
  }

  private getUserAuthHeaders(user: User) {
    return {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Authorization': 'Basic ' + btoa(user.username + ':' + user.password)
      })
    };
  }
}
