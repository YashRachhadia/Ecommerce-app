import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  testProductData = {
    id: 1,
    name: 'xyz',
    category: 'asdfgh',
    description:
      'qweuqir aksdfkalsd kslkdfjas df asdfhioqwehr iurhqwrnkaknsdf asdflkasfd asdf',
    status: 'available',
    stock: '10',
    manufacturer: 'def',
    price: 50,
    productImage: '../../assets/images/pimage.jpeg',
  };
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getProductTest() {
    return this.testProductData;
  }

  addProduct(formData: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/addProduct`, formData);
  }

  getProduct(productId): Observable<any> {
    return this.http.get(`${this.baseUrl}/product/${productId}`);
  }

  getAllProducts(): Observable<any> {
    return this.http.get(`${this.baseUrl}/getProducts`);
  }

  updateProduct(productId, formData): Observable<any> {
    return this.http.put(`${this.baseUrl}/product/${productId}`, formData);
  }

  deleteProduct(productId): Observable<any> {
    return this.http.delete(`${this.baseUrl}/product/${productId}`, {
      responseType: 'text',
    });
  }
}
