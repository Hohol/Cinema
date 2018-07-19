import {Component, OnInit} from '@angular/core';
import {User} from '../../../model/model.user';
import {ApiService} from '../../../services/api.service';


@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: User[];

  constructor(private api: ApiService) {
  }

  ngOnInit() {
    this.api.getUsers()
      .subscribe(users => this.users = users);
  }
}
