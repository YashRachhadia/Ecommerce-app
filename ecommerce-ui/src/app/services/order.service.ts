import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(private http: HttpClient) {}

  private baseUrl = environment.apiUrl;

  paybyCod(cartId): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/orderConfirmation/paybyCod/${cartId}`,
      null,
      { responseType: 'text' }
    );
  }

  payOnline(cartId): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/orderConfirmation/payOnline/${cartId}`,
      null,
      { responseType: 'text' }
    );
  }

  getPendingOrders(): Observable<any> {
    return this.http.get(`${this.baseUrl}/pendingOrders`);
  }

  getOrdersHistory(): Observable<any> {
    return this.http.get(`${this.baseUrl}/allOrders`);
  }

  setOrderFulfilled(confirmOrderId): Observable<any> {
    return this.http.put(
      `${this.baseUrl}/pendingOrders/setFulfilled/${confirmOrderId}`,
      null
    );
  }
}
