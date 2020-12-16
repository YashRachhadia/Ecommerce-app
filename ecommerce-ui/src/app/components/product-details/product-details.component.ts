import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css'],
})
export class ProductDetailsComponent implements OnInit {
  product: any;
  error: boolean = false;
  success: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<ProductDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router,
    private cartService: CartService,
    public authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.product = this.data.productDetails;
  }

  addtoCart(product) {
    if (this.authenticationService.isUserLoggedIn()) {
      console.log(sessionStorage.getItem('username'));
      this.cartService
        .addItemToCart(product.productId, sessionStorage.getItem('username'))
        .subscribe(
          (res) => {
            console.log(res);
            if (!this.success) {
              this.success = !this.success;
            }
            if (this.error) {
              this.error = !this.error;
            }
          },
          (error) => console.log(error)
        );
    } else {
      if (!this.error) {
        this.error = !this.error;
      }
      if (this.success) {
        this.success = !this.success;
      }
    }
  }

  viewCart() {
    this.dialogRef.close();
    this.router.navigate(['customer/cart']);
  }
}
