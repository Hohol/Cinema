import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MessageService} from '../../../services/message.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SeanceService} from '../../../services/seance.service';
import {Moment} from 'moment';
import {Movie} from '../../../model/model.movie';
import {Hall} from '../../../model/model.hall';
import {HallService} from '../../../services/hall.service';
import {MovieService} from '../../../services/movie.service';
import {Seance} from '../../../model/model.seance';
import * as moment from 'moment';

@Component({
  selector: 'app-create-seance',
  templateUrl: './create-seance.component.html',
  styleUrls: ['./create-seance.component.css'],
  encapsulation: ViewEncapsulation.None, // some magic required for dateTimePicker...
})
export class EditSeanceComponent implements OnInit {

  componentTitle = 'Редактирование сеанса';
  actionTitle = 'Сохранить';
  successMessage = 'Изменения сохранены';

  seance: Seance;

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
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit() {
    this.movieService.getMovies()
      .subscribe(movies => this.movies = movies);
    this.hallService.getHalls()
      .subscribe(halls => this.halls = halls);
    const id = +this.route.snapshot.paramMap.get('id');
    this.seanceService.getSeance(id)
      .subscribe(seance => {
        this.seance = seance;
        this.movieId = seance.movie.id;
        this.startTime = moment(seance.startTime);
        this.hallId = seance.hall.id;
      });
  }

  cancel() {
    this.router.navigate(['/seances']);
  }

  action() {
    const time = this.startTime.toISOString();
    const changedSeance = {
      id: this.seance.id,
      startTime: time,
      movieId: this.movieId,
      hallId: this.hallId
    };
    this.seanceService.editSeance(changedSeance)
      .subscribe(r => {
        this.messageService.setSuccessMessage(this.successMessage);
        this.router.navigate(['/seances']);
      });
  }
}
