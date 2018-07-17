import {Seance} from './model.seance';
import {User} from './model.user';
import {Position} from './model.position';

export class Ticket {
  id: number;
  seance: Seance;
  user: User;
  position: Position;
  price: number;
}
