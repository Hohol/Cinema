import {Component, OnInit} from '@angular/core';
import {MessageService} from '../../../services/message.service';
import {Hall} from '../../../model/model.hall';
import {HallService} from '../../../services/hall.service';

@Component({
  selector: 'app-halls',
  templateUrl: './halls.component.html',
  styleUrls: ['./halls.component.css']
})
export class HallsComponent implements OnInit {

  halls: Hall[];

  constructor(private hallService: HallService, private messageService: MessageService) {
  }

  ngOnInit() {
    this.hallService.getHalls()
      .subscribe(halls => this.halls = halls);
  }
}
