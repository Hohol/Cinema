import {ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MessageService} from '../../../services/message.service';
import {Router} from '@angular/router';
import {SeanceService} from '../../../services/seance.service';
import {Moment} from 'moment';

@Component({
  selector: 'app-create-seance',
  templateUrl: './create-seance.component.html',
  styleUrls: ['./create-seance.component.css'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CreateSeanceComponent implements OnInit {

  componentTitle = 'Добавление нового сеанса';
  actionTitle = 'Добавить';
  successMessage = 'Сеанс создан';

  startTime: Moment;
  movieId: number;
  hallId: number;

  constructor(
    private seanceService: SeanceService,
    private messageService: MessageService,
    private router: Router,
  ) {
  }

  ngOnInit() {
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
