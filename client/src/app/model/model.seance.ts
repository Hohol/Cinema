import {Movie} from './model.movie';
import * as moment from 'moment';
import {Hall} from './model.hall';

export class Seance {
  id: number;
  movie: Movie;
  hall: Hall;
  startTime: string;
  price: number;

  public formattedStartTime(): string {
    return moment(this.startTime).format('YYYY-MM-DD HH:mm');
  }
}
