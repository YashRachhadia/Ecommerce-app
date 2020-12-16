import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from 'src/app/services/cart.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  constructor(
    private router: Router,
    private cartService: CartService // private userService: UserService
  ) {}

  cart: any;
  cartItems: any = [];
  error: boolean = false;

  ngOnInit(): void {
    console.log(sessionStorage.getItem('username'));
    this.cartService.getCart(sessionStorage.getItem('username')).subscribe(
      (res) => {
        console.log('Cart', res);
        this.cart = res;
      },
      (error) => console.log(error)
    );
    this.cartService
      .getAllCartItems(sessionStorage.getItem('username'))
      .subscribe(
        (res) => {
          console.log('CartItems', res);
          this.cartItems = res;
        },
        (error) => {
          console.log('error', error);
        }
      );
  }

  checkout(cartId) {
    this.cartService.createOrder(cartId).subscribe(
      (res) => {
        console.log(res);
        if (this.error) {
          this.error = !this.error;
        }
      },
      (error) => {
        console.log(error);
        if (!this.error) {
          this.error = !this.error;
        }
      }
    );
    this.cartService.checkOutFromCart(cartId).subscribe(
      (res) => {
        console.log(res);
        if (this.error) {
          this.error = !this.error;
        }
        this.router.navigate(['customer/order/checkout', cartId]);
      },
      (error) => {
        console.log(error);
        if (!this.error) {
          this.error = !this.error;
        }
      }
    );
  }

  removeItemFromCart(productId) {
    this.cartService.removeCartItem(productId).subscribe(
      (res) => {
        console.log(res);
        this.ngOnInit();
      },
      (error) => console.log(error)
    );
  }

  clearCart(cartId) {
    this.cartService.clearCart(cartId).subscribe(
      (res) => {
        console.log(res);
        this.ngOnInit();
      },
      (error) => console.log(error)
    );
    this.router.navigate(['customer/cart']);
  }

  calculateGrandTotal() {
    let grandTotal = 0;
    for (let item of this.cartItems) {
      grandTotal += item.totalPrice;
    }
    return grandTotal;
  }
}
