import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {Seance} from '../../model/model.seance';

@Component({
  selector: 'app-seances',
  templateUrl: './seances.component.html',
  styleUrls: ['./seances.component.css']
})
export class SeancesComponent implements OnInit {

  seances: Seance[];

  constructor(private api: ApiService) {
  }

  ngOnInit() {
    console.log('seances oninit');
    this.api.getSeances()
      .subscribe(seances => this.seances = seances);
  }
}
