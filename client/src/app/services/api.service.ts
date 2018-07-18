import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Seance} from '../model/model.seance';
import {map} from 'rxjs/operators';
import {User} from '../model/model.user';
import {Position} from '../model/model.position';
import {UserStats} from '../model/model.user-stats';
import {SeanceStats} from '../model/model.seance-stats';
import {BaseApiService} from './base-api.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService extends BaseApiService {

  constructor(http: HttpClient) {
    super(http);
  }

  getSeances(movieId?: number) {
    const url = '/seances' + (movieId ? `?movieId=${movieId}` : '');
    return this.get<Seance[]>(url)
      .pipe(map(seances => seances.map(this.toSeance)));
  }

  getSeance(id: number) {
    return this.get(`/seance/${id}`)
      .pipe(map(this.toSeance));
  }

  getSeanceStats(seanceId: number): Observable<SeanceStats> {
    return this.get(`/seance-stats/${seanceId}`);
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
