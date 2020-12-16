import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css'],
})
export class UpdateProductComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  updateProductForm: FormGroup;
  error: boolean = false;
  productImage: any = File;
  productId: any;

  ngOnInit(): void {
    this.updateProductForm = this.fb.group({
      productName: ['', Validators.required],
      productCategory: ['', Validators.required],
      productDescription: ['', Validators.required],
      status: ['', Validators.required],
      stock: [undefined, Validators.required],
      manufacturer: ['', Validators.required],
      price: ['', Validators.required],
    });
    this.productId = this.route.snapshot.params['productId'];
    this.productService.getProduct(this.productId).subscribe(
      (res) => {
        console.log('Product to be updated', res);
        this.updateProductForm.setValue({
          productName: res.productName,
          productCategory: res.productCategory,
          productDescription: res.productDescription,
          status: res.status,
          stock: res.stock,
          manufacturer: res.manufacturer,
          price: res.price,
        });
      },
      (error) => console.log(error)
    );
  }

  onFileChange(event) {
    const file = event.target.files[0];
    console.log(file);
    this.productImage = file;
  }

  updateProduct(productFormData: FormGroup) {
    if (this.updateProductForm.valid) {
      const data = productFormData.value;
      const formData = new FormData();
      formData.append('product', JSON.stringify(data));
      formData.append('file', this.productImage);
      this.productService.updateProduct(this.productId, formData).subscribe(
        (res) => {
          console.log('Updated Product', res);
          this.router.navigate(['admin/productInventory']);
        },
        (error) => {
          console.log(error);
          if (!this.error) {
            this.error = !this.error;
          }
        }
      );
    }
  }
}
