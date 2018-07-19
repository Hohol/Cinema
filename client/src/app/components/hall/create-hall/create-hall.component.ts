import {Component, OnInit} from '@angular/core';
import {MessageService} from '../../../services/message.service';
import {Router} from '@angular/router';
import {HallService} from '../../../services/hall.service';
import {Hall} from '../../../model/model.hall';
import _ from 'lodash';

@Component({
  selector: 'app-create-hall',
  templateUrl: './create-hall.component.html',
  styleUrls: ['./create-hall.component.css']
})
export class CreateHallComponent implements OnInit {

  componentTitle = 'Добавление нового зала';
  actionTitle = 'Добавить';
  successMessage = 'Зал создан';

  hall: Hall = new Hall();

  _ = _; // make lodash available in template

  constructor(
    private hallService: HallService,
    private messageService: MessageService,
    private router: Router,
  ) {
    this.hall.vipPositions = [];
  }

  ngOnInit() {
  }

  cancel() {
    this.router.navigate(['/halls']);
  }

  action() {
    this.hallService.createHall(this.hall)
      .subscribe(r => {
        this.messageService.setSuccessMessage(this.successMessage);
        this.router.navigate(['/halls']);
      });
  }

  click(row: number, col: number) {
    const pos = {row: row, col: col};
    if (this.contains(this.hall.vipPositions, pos)) {
      _.remove(this.hall.vipPositions, pos);
    } else {
      this.hall.vipPositions.push(pos);
    }
  }

  private getColor(row: number, col: number) {
    const pos = {row: row, col: col};
    if (this.contains(this.hall.vipPositions, pos)) {
      return 'red';
    }
    return 'white';
  }

  private contains(ar, pos) {
    return _.findIndex(ar, pos) !== -1;
  }
}
