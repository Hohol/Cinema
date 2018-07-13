import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Movie} from '../model/model.movie';
import {Observable} from 'rxjs';
import {Seance} from '../model/model.seance';
import {map} from 'rxjs/operators';
import {User} from '../model/model.user';
import {Position} from '../model/model.position';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  static API_URL = 'http://localhost:8090';

  constructor(private http: HttpClient) {
  }

  getMovies(): Observable<Movie[]> {
    return this.get<Movie[]>('/movies');
  }

  getSeances() {
    return this.get<Seance[]>('/seances')
      .pipe(map(seances => seances.map(this.toSeance)));
  }

  getSeance(id: number) {
    return this.get(`/seance/${id}`)
      .pipe(map(this.toSeance));
  }

  calculatePrice(seance: Seance, selected: Position[]): Observable<number> {
    return this.post<number>(`/seance/calculate-price/${seance.id}`, selected);
  }

  buyTickets(seance: Seance, selected: Position[]) {
    return this.post(`/seance/buy/${seance.id}`, selected);
  }

  toSeance(s: Seance) {
    return Object.assign(new Seance(), s);
  }

  createMovie(movie: Movie) {
    return this.post<Seance[]>('/movies/create', movie);
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

  createAccount(user: User) {
    return this.http.post(ApiService.API_URL + '/account/register', user);
  }

  private get<T>(url) {
    return this.http.get<T>(ApiService.API_URL + url, this.getAuthHeaders());
  }

  private post<T>(url, body) {
    return this.http.post<T>(ApiService.API_URL + url, body, this.getAuthHeaders());
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
