import {Movie} from './model.movie';
import * as moment from 'moment';

export class Seance {
  id: number;
  movie: Movie;
  startTime: string;
  price: number;

  public formattedStartTime(): string {
    return moment(this.startTime).format('YYYY-MM-DD HH:mm');
  }
}
