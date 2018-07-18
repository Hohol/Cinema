import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Movie} from '../model/model.movie';
import {Seance} from '../model/model.seance';
import {HttpClient} from '@angular/common/http';
import {BaseApiService} from './base-api.service';

@Injectable({
  providedIn: 'root'
})
export class MovieService extends BaseApiService {

  constructor(http: HttpClient) {
    super(http);
  }

  public getMovies(): Observable<Movie[]> {
    return this.get<Movie[]>('/movies');
  }

  createMovie(movie: Movie) {
    return this.post<Seance[]>('/movies/create', movie);
  }
}
