import {Component, OnInit} from '@angular/core';
import {MessageService} from '../../../services/message.service';
import {MovieService} from '../../../services/movie.service';
import {Movie} from '../../../model/model.movie';
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-movie',
  templateUrl: './create-movie.component.html',
  styleUrls: ['./create-movie.component.css']
})
export class CreateMovieComponent implements OnInit {

  componentTitle = 'Добавление нового фильма';
  actionTitle = 'Добавить';
  successMessage = 'Фильм создан';

  movie: Movie = new Movie();

  constructor(
    private movieService: MovieService,
    private messageService: MessageService,
    private router: Router,
  ) {
  }

  ngOnInit() {
  }

  cancel() {
    this.router.navigate(['/movies']);
  }

  action() {
    this.movieService.createMovie(this.movie)
      .subscribe(r => {
        this.messageService.setSuccessMessage(this.successMessage);
        this.router.navigate(['/movies']);
      });
  }
}
