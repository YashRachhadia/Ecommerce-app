import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private cartService: CartService
  ) {}

  cartId: any;
  cart: any;
  cartItems: any;

  ngOnInit(): void {
    this.cartId = this.route.snapshot.params['cartId'];
    this.cartService.getCartById(this.cartId).subscribe(
      (res) => (this.cart = res),
      (error) => console.log(error)
    );
    this.cartService
      .getAllCartItems(sessionStorage.getItem('username'))
      .subscribe(
        (res) => (this.cartItems = res),
        (error) => console.log(error)
      );
  }

  goToPayment(cartId) {
    this.router.navigate(['customer/order/payment', cartId]);
  }

  getCurrentDate() {
    return new Date();
  }
}
