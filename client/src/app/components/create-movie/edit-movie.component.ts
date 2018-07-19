import {Component, OnInit} from '@angular/core';
import {MessageService} from '../../services/message.service';
import {MovieService} from '../../services/movie.service';
import {Movie} from '../../model/model.movie';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-edit-movie',
  templateUrl: './create-movie.component.html',
  styleUrls: ['./create-movie.component.css']
})
export class EditMovieComponent implements OnInit {

  componentTitle = 'Редактировать фильм';
  actionTitle = 'Сохранить';
  successMessage = 'Изменения сохранены';

  movie: Movie = new Movie();

  constructor(
    private movieService: MovieService,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    const movieId = +this.route.snapshot.paramMap.get('id');
    this.movieService.getMovie(movieId)
      .subscribe(movie => this.movie = movie);
  }

  cancel() {
    this.router.navigate(['/movies']);
  }

  action() {
    this.movieService.editMovie(this.movie)
      .subscribe(r => {
        this.messageService.setSuccessMessage(this.successMessage);
        this.router.navigate(['/movies']);
      });
  }
}
