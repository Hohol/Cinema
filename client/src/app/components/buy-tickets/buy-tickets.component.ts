import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Seance} from '../../model/model.seance';
import {ApiService} from '../../services/api.service';
import {Position} from '../../model/model.position';
import _ from 'lodash';

@Component({
  selector: 'app-buy-tickets',
  templateUrl: './buy-tickets.component.html',
  styleUrls: ['./buy-tickets.component.css']
})
export class BuyTicketsComponent implements OnInit {

  private selectedColor = '#ADD8E6';
  private occupiedColor = 'red';
  private vipColor = 'gold';
  private defaultColor = 'white';

  _ = _; // о_О

  seance: Seance;
  selected: Position[] = [];
  price: number;

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

  private click(row: number, col: number) {
    const pos = {row: row, col: col};
    if (this.contains(this.seance.occupiedPositions, pos)) {
      return;
    }
    if (!this.contains(this.selected, pos)) {
      this.selected.push(pos);
    } else {
      _.remove(this.selected, pos);
    }
    this.api.calculatePrice(this.seance, this.selected)
      .subscribe(price => this.price = price); // todo race condition
  }

  private getColor(row: number, col: number) {
    const pos = {row: row, col: col};
    if (this.contains(this.seance.occupiedPositions, pos)) {
      return this.occupiedColor;
    }
    if (this.contains(this.selected, pos)) {
      return this.selectedColor;
    }
    if (this.contains(this.seance.hall.vipPositions, pos)) {
      return this.vipColor;
    }
    return this.defaultColor;
  }

  private contains(ar, pos) {
    return _.findIndex(ar, pos) !== -1;
  }

  showSelected(): string {
    return this.selected
      .map(p => `Ряд ${p.row} Место ${p.col}`)
      .join('; ');
  }

  buy() {

  }
}
