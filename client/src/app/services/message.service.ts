import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  message: string;
  alertType: string;

  constructor() {
  }

  setSuccessMessage(message: string) {
    this.message = message;
    this.alertType = 'alert-success';
  }

  setErrorMessage(message: string) {
    this.message = message;
    this.alertType = 'alert-danger';
  }

  clear() {
    this.message = null;
  }
}
