import {Component, OnInit} from '@angular/core';
import {Movie} from '../../../model/model.movie';
import {User} from '../../../model/model.user';
import {MovieService} from '../../../services/movie.service';
import {MessageService} from '../../../services/message.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {

  movies: Movie[];
  currentUser: User;

  constructor(private movieService: MovieService, private messageService: MessageService) {
  }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.reloadMovies();
  }

  private reloadMovies() {
    this.movieService.getMovies()
      .subscribe(movies => this.movies = movies);
  }

  delete(movieId: number) {
    this.movieService.deleteMovie(movieId)
      .subscribe(
        r => {
          this.reloadMovies();
          this.messageService.setSuccessMessage('Фильм удален');
        },
        r => {
          this.messageService.setErrorMessage('Не удалось удалить фильм: ' + r.error.errorMessage);
        }
      );
  }
}
