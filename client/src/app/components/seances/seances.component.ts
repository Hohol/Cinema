import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {Seance} from '../../model/model.seance';
import * as moment from 'moment';

@Component({
  selector: 'app-seances',
  templateUrl: './seances.component.html',
  styleUrls: ['./seances.component.css']
})
export class SeancesComponent implements OnInit {

  seances: Seance[];

  public convertDate(startTime: string): string {
    return moment(startTime).format('YYYY-MM-DD HH:mm');
  }

  constructor(private api: ApiService) {
  }

  ngOnInit() {
    this.api.getSeances()
      .subscribe(seances => this.seances = seances);
  }
}
