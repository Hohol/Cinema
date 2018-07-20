import {Component, OnInit} from '@angular/core';
import {Movie} from '../../../model/model.movie';
import {User} from '../../../model/model.user';
import {MovieService} from '../../../services/movie.service';
import {MessageService} from '../../../services/message.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {

  movies: Movie[];
  currentUser: User;
  filter: string;

  constructor(
    private movieService: MovieService,
    private messageService: MessageService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.route.queryParams
      .subscribe(params => {
        this.filter = params['filter'] || 'ongoing';
        this.reloadMovies();
      });
  }

  private reloadMovies() {
    this.movieService.getMovies(this.filter)
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

  active(filter: string) {
    return filter === this.filter ? 'active' : '';
  }
}
