import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {SeanceStats} from '../../../model/model.seance-stats';
import {SeanceService} from '../../../services/seance.service';

@Component({
  selector: 'app-seance-stats',
  templateUrl: './seance-stats.component.html',
  styleUrls: ['./seance-stats.component.css']
})
export class SeanceStatsComponent implements OnInit {

  seanceStats: SeanceStats;

  constructor(private seanceService: SeanceService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    const seanceId = +this.route.snapshot.paramMap.get('id');
    this.seanceService.getSeanceStats(seanceId)
      .subscribe(seanceStats => this.seanceStats = seanceStats);
  }
}
