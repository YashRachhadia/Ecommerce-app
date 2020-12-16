import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],
})
export class AddProductComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private productService: ProductService
  ) {}
  addProductForm: FormGroup;
  success: boolean = false;
  productImage: any = File;

  ngOnInit(): void {
    this.addProductForm = this.fb.group({
      productName: ['', Validators.required],
      productCategory: ['', Validators.required],
      productDescription: ['', Validators.required],
      status: ['', Validators.required],
      stock: [undefined, Validators.required],
      manufacturer: ['', Validators.required],
      price: ['', Validators.required],
    });
  }

  onFileChange(event) {
    const file = event.target.files[0];
    console.log(file);
    this.productImage = file;
  }

  addProduct(productFormData: FormGroup) {
    if (this.addProductForm.valid) {
      const data = productFormData.value;
      const formData = new FormData();
      formData.append('product', JSON.stringify(data));
      formData.append('file', this.productImage);
      this.productService.addProduct(formData).subscribe(
        (res) => {
          console.log(res);
          if (!this.success) {
            this.success = !this.success;
          }
          this.addProductForm.reset();
        },
        (error) => console.log(error)
      );
    } else {
      if (this.success) {
        this.success = !this.success;
      }
    }
  }
}
