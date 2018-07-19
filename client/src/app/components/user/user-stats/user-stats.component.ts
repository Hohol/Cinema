import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {UserStats} from '../../../model/model.user-stats';
import {ApiService} from '../../../services/api.service';

@Component({
  selector: 'app-user-tickets',
  templateUrl: './user-stats.component.html',
  styleUrls: ['./user-stats.component.css']
})
export class UserStatsComponent implements OnInit {

  userStats: UserStats;

  constructor(private api: ApiService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    const userId = +this.route.snapshot.paramMap.get('id');
    this.api.getUserStats(userId)
      .subscribe(userStats => this.userStats = userStats);
  }
}
