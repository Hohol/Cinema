import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {Seance} from '../../model/model.seance';
import {ActivatedRoute} from '@angular/router';
import {User} from '../../model/model.user';

@Component({
  selector: 'app-seances',
  templateUrl: './seances.component.html',
  styleUrls: ['./seances.component.css']
})
export class SeancesComponent implements OnInit {

  seances: Seance[];
  currentUser: User;

  constructor(private api: ApiService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.route.queryParams
      .subscribe(params => {
        this.api.getSeances(params['movieId'])
          .subscribe(seances => {
            this.seances = seances;
          });
      });
  }
}
