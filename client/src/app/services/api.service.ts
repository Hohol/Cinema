import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppComponent} from '../app.component';
import {Movie} from '../model/model.movie';
import {Observable} from 'rxjs';
import {Seance} from '../model/model.seance';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  static API_URL = 'http://localhost:8090';

  constructor(private http: HttpClient) {
  }

  getMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(ApiService.API_URL + '/movies');
  }

  getSeances(): Observable<Seance[]> {
    return this.http.get<Seance[]>(ApiService.API_URL + '/seances');
  }
}
