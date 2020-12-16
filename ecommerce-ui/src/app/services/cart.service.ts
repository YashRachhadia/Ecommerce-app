import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  addItemToCart(productId, username) {
    return this.http.post(`${this.baseUrl}/cart/add/${productId}`, username, {
      responseType: 'text',
    });
  }

  getCart(loggedUser: string) {
    return this.http.get(`${this.baseUrl}/getCart`, {
      params: new HttpParams().set('username', loggedUser),
    });
  }

  getCartById(cartId: number) {
    return this.http.get(`${this.baseUrl}/getCartById/${cartId}`);
  }

  removeCartItem(productId) {
    return this.http.delete(`${this.baseUrl}/cart/remove/${productId}`, {
      responseType: 'text',
    });
  }

  clearCart(cartId) {
    return this.http.delete(`${this.baseUrl}/cart/clear/${cartId}`, {
      responseType: 'text',
    });
  }

  createOrder(cartId) {
    return this.http.post(`${this.baseUrl}/order/${cartId}`, null);
  }

  checkOutFromCart(cartId) {
    return this.http.put(`${this.baseUrl}/checkout/${cartId}`, null);
  }

  getAllCartItems(username) {
    return this.http.get(`${this.baseUrl}/getCartItems`, {
      params: new HttpParams().set('username', username),
    });
  }
}
