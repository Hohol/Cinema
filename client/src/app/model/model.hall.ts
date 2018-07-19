import {Position} from './model.position';

export class Hall {
  id: number;
  name: string;
  vipFactor: number;
  rowCnt: number;
  colCnt: number;
  vipPositions: Position[];
}
