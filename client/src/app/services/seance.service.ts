import {Injectable} from '@angular/core';
import {BaseApiService} from './base-api.service';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {SeanceStats} from '../model/model.seance-stats';
import {Seance} from '../model/model.seance';
import {Observable} from 'rxjs';
import {Position} from '../model/model.position';

@Injectable({
  providedIn: 'root'
})
export class SeanceService extends BaseApiService {

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

  deleteSeance(id: number): Observable<Object> {
    return this.post(`/seances/delete/${id}`, {});
  }

  createSeance(seance) {
    return this.post('/seances/create', seance);
  }

  editSeance(seance) {
    return this.post('/seances/edit', seance);
  }
}
