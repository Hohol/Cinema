import {Component, OnInit} from '@angular/core';
import {Seance} from '../../model/model.seance';
import {ActivatedRoute} from '@angular/router';
import {User} from '../../model/model.user';
import {SeanceService} from '../../services/seance.service';
import {MessageService} from '../../services/message.service';

@Component({
  selector: 'app-seances',
  templateUrl: './seances.component.html',
  styleUrls: ['./seances.component.css']
})
export class SeancesComponent implements OnInit {

  seances: Seance[];
  currentUser: User;

  constructor(private seanceService: SeanceService, private route: ActivatedRoute, private messageService: MessageService) {
  }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.route.queryParams
      .subscribe(params => {
        this.reloadSeances(params['movieId']);
      });
  }

  delete(id: number) {
    this.seanceService.deleteSeance(id)
      .subscribe(r => {
        this.reloadSeances();
        this.messageService.setSuccessMessage('Сеанс удален');
      });
  }

  private reloadSeances(movieId?: number) {
    this.seanceService.getSeances(movieId)
      .subscribe(seances => {
        this.seances = seances;
      });
  }
}
