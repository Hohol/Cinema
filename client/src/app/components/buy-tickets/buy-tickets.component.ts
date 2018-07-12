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

  private selectedColor = 'yellow';
  private takenColor = 'red';
  private vipColor = 'pink';
  private defaultColor = 'white';

  _ = _; // о_О

  seance: Seance;
  selected: Position[] = [];

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
    this.selected.push({row: row, col: col});
  }

  private getColor(row: number, col: number) {
    const pos = {row: row, col: col};
    if (this.contains(this.selected, pos)) {
      return this.selectedColor;
    }
    if (this.contains(this.seance.hall.vipPositions, pos)) {
      return this.vipColor;
    }
    return this.defaultColor;
  }

  private contains(ar, pos) {
    return ar.some(p => p.row === pos.row && p.col === pos.col);
  }
}
