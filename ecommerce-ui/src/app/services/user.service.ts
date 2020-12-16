import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}
  private baseUrl = environment.apiUrl;

  getAllCustomers(): Observable<any> {
    return this.http.get(`${this.baseUrl}/getCustomers`);
  }

  addCustomers(customer): Observable<any> {
    return this.http.post(`${this.baseUrl}/addCustomer`, customer);
  }

  deleteCustomer(customerId): Observable<any> {
    return this.http.delete(`${this.baseUrl}/customer/${customerId}`, {
      responseType: 'text',
    });
  }
}
