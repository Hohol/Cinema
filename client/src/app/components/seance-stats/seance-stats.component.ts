import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {ActivatedRoute} from '@angular/router';
import {SeanceStats} from '../../model/model.seance-stats';

@Component({
  selector: 'app-seance-stats',
  templateUrl: './seance-stats.component.html',
  styleUrls: ['./seance-stats.component.css']
})
export class SeanceStatsComponent implements OnInit {

  seanceStats: SeanceStats;

  constructor(private api: ApiService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    const seanceId = +this.route.snapshot.paramMap.get('id');
    this.api.getSeanceStats(seanceId)
      .subscribe(seanceStats => this.seanceStats = seanceStats);
  }
}
