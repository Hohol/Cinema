import {Component, OnInit} from '@angular/core';
import {Seance} from '../../model/model.seance';
import {ActivatedRoute} from '@angular/router';
import {User} from '../../model/model.user';
import {SeanceService} from '../../services/seance.service';

@Component({
  selector: 'app-seances',
  templateUrl: './seances.component.html',
  styleUrls: ['./seances.component.css']
})
export class SeancesComponent implements OnInit {

  seances: Seance[];
  currentUser: User;

  constructor(private seanceService: SeanceService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.route.queryParams
      .subscribe(params => {
        this.seanceService.getSeances(params['movieId'])
          .subscribe(seances => {
            this.seances = seances;
          });
      });
  }
}
