import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Movie} from '../model/model.movie';
import {HttpClient} from '@angular/common/http';
import {BaseApiService} from './base-api.service';

@Injectable({
  providedIn: 'root'
})
export class MovieService extends BaseApiService {

  constructor(http: HttpClient) {
    super(http);
  }

  public getMovies(filter: string): Observable<Movie[]> {
    return this.get<Movie[]>(`/movies?filter=${filter}`);
  }

  getMovie(id: number) {
    return this.get<Movie>(`/movies/${id}`);
  }

  createMovie(movie: Movie) {
    return this.post('/movies/create', movie);
  }

  deleteMovie(id: number): Observable<Object> {
    return this.post(`/movies/delete/${id}`, {});
  }

  editMovie(movie: Movie) {
    return this.post(`/movies/edit/`, movie);
  }
}
