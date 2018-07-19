import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../model/model.user';
import {Observable} from 'rxjs';

export class BaseApiService {

  protected static API_URL = 'http://localhost:8090';

  constructor(protected http: HttpClient) {
  }

  protected get<T>(url): Observable<T> {
    return this.http.get<T>(BaseApiService.API_URL + url, this.getAuthHeaders());
  }

  protected post<T>(url, body) {
    return this.http.post<T>(BaseApiService.API_URL + url, body, this.getAuthHeaders());
  }

  private getAuthHeaders() {
    const user = JSON.parse(localStorage.getItem('currentUser'));
    if (!user) {
      return {};
    }
    return this.getUserAuthHeaders(user);
  }

  protected getUserAuthHeaders(user: User) {
    return {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Authorization': 'Basic ' + btoa(user.username + ':' + user.password)
      })
    };
  }
}
