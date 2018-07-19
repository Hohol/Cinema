import {Injectable} from '@angular/core';
import {BaseApiService} from './base-api.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Hall} from '../model/model.hall';

@Injectable({
  providedIn: 'root'
})
export class HallService extends BaseApiService {

  constructor(http: HttpClient) {
    super(http);
  }

  getHalls(): Observable<Hall[]> {
    return this.get<Hall[]>('/halls');
  }

  deleteHall(id: number) {
    return this.post(`/halls/delete/${id}`, {});
  }

  createHall(hall: Hall) {
    return this.post('/halls/create', hall);
  }
}
