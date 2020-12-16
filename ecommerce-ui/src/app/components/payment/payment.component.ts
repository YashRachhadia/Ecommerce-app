import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css'],
})
export class PaymentComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cartService: CartService,
    private orderService: OrderService,
    private fb: FormBuilder
  ) {}

  cartId: any;
  cart: any;
  error: boolean = false;
  success: boolean = false;
  onlinePaymentForm: FormGroup;

  get cardNum() {
    return this.onlinePaymentForm.get('cardNum');
  }

  get cvv() {
    return this.onlinePaymentForm.get('cvv');
  }

  ngOnInit(): void {
    this.onlinePaymentForm = this.fb.group({
      cardNum: [
        '',
        [
          Validators.required,
          Validators.minLength(16),
          Validators.maxLength(16),
          Validators.pattern('^[0-9]{16}'),
        ],
      ],
      cvv: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(3),
          Validators.pattern('^[0-9]{3}'),
        ],
      ],
    });
    this.cartId = this.route.snapshot.params['cartId'];
    this.cartService.getCartById(this.cartId).subscribe(
      (res) => (this.cart = res),
      (error) => console.log(error)
    );
  }

  payByOnline() {
    if (this.cart.grandTotal > 0) {
      if (this.onlinePaymentForm.valid) {
        this.orderService.payOnline(this.cartId).subscribe(
          (res) => {
            console.log(res);
            if (!this.success) {
              this.success = !this.success;
            }
            if (this.error) {
              this.error = !this.error;
            }
            this.onlinePaymentForm.reset();
            this.ngOnInit();
          },
          (error) => {
            console.log(error);
            if (!this.error) {
              this.error = !this.error;
            }
            if (this.success) {
              this.success = !this.success;
            }
          }
        );
      }
    }
  }

  payByCod() {
    if (this.cart.grandTotal > 0) {
      this.orderService.paybyCod(this.cartId).subscribe(
        (res) => {
          console.log(res);
          if (!this.success) {
            this.success = !this.success;
          }
          if (this.error) {
            this.error = !this.error;
          }
          this.ngOnInit();
        },
        (error) => {
          console.log(error);
          if (!this.error) {
            this.error = !this.error;
          }
          if (this.success) {
            this.success = !this.success;
          }
        }
      );
    }
  }

  goBack() {
    this.router.navigate(['customer/order/checkout', this.cartId]);
  }
}
