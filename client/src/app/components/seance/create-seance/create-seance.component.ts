import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MessageService} from '../../../services/message.service';
import {Router} from '@angular/router';
import {SeanceService} from '../../../services/seance.service';
import {Moment} from 'moment';
import {Movie} from '../../../model/model.movie';
import {Hall} from '../../../model/model.hall';
import {HallService} from '../../../services/hall.service';
import {MovieService} from '../../../services/movie.service';

@Component({
  selector: 'app-create-seance',
  templateUrl: './create-seance.component.html',
  styleUrls: ['./create-seance.component.css'],
  encapsulation: ViewEncapsulation.None, // some magic required for dateTimePicker...
})
export class CreateSeanceComponent implements OnInit {

  componentTitle = 'Добавление нового сеанса';
  actionTitle = 'Добавить';
  successMessage = 'Сеанс создан';

  startTime: Moment;
  movieId: number;
  hallId: number;

  movies: Movie[];
  halls: Hall[];

  constructor(
    private seanceService: SeanceService,
    private hallService: HallService,
    private movieService: MovieService,
    private messageService: MessageService,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.movieService.getMovies('all')
      .subscribe(movies => this.movies = movies);
    this.hallService.getHalls()
      .subscribe(halls => this.halls = halls);
  }

  cancel() {
    this.router.navigate(['/seances']);
  }

  action() {
    const time = this.startTime.toISOString();
    this.seanceService.createSeance({startTime: time, movieId: this.movieId, hallId: this.hallId})
      .subscribe(r => {
        this.messageService.setSuccessMessage(this.successMessage);
        this.router.navigate(['/seances']);
      });
  }
}
