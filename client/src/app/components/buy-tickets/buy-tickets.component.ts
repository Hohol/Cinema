import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Seance} from '../../model/model.seance';
import {ApiService} from '../../services/api.service';

@Component({
  selector: 'app-buy-tickets',
  templateUrl: './buy-tickets.component.html',
  styleUrls: ['./buy-tickets.component.css']
})
export class BuyTicketsComponent implements OnInit {

  seance: Seance;

  constructor(private route: ActivatedRoute, private api: ApiService) {
  }

  ngOnInit() {
    this.getSeance();
  }

  private getSeance() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.api.getSeance(id)
      .subscribe(s => this.seance = s);
  }
}
