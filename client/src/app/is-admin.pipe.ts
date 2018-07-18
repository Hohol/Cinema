import {Pipe, PipeTransform} from '@angular/core';
import {User} from './model/model.user';

@Pipe({name: 'isAdmin'})
export class IsAdminPipe implements PipeTransform {
  transform(user: User): boolean {
    return user && user.role === 'ADMIN';
  }
}
