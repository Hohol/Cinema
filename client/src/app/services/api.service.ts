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

  constructor(private http: HttpClient) {
  }

  getMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(AppComponent.API_URL + '/movies');
  }

  getSeances(): Observable<Seance[]> {
    return this.http.get<Seance[]>(AppComponent.API_URL + '/seances');
  }
}
