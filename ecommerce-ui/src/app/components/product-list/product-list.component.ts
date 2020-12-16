import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { ProductService } from 'src/app/services/product.service';
import { ProductDetailsComponent } from '../product-details/product-details.component';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit, AfterViewInit, OnDestroy {
  constructor(
    private productService: ProductService,
    public viewProductBox: MatDialog
  ) {}

  @ViewChild(DataTableDirective, { static: false })
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: DataTables.Settings = {};

  // productData = [];
  allProducts: any;

  ngOnInit(): void {
    this.dtOptions = {
      lengthMenu: [
        [10, 25, 50, -1],
        [10, 25, 50, 'All'],
      ],
    };
    // this.productData.push(this.productService.getProductTest());
    this.loadData();
  }

  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  viewProduct(product) {
    this.viewProductBox.open(ProductDetailsComponent, {
      width: '600px',
      height: '550px',
      data: {
        productDetails: product,
      },
    });
  }

  loadData() {
    this.productService.getAllProducts().subscribe(
      (res) => {
        this.allProducts = res;
        for (let p in this.allProducts) {
          let base64Data = this.allProducts[p].productImage;
          this.allProducts[p].productImage =
            'data:image/jpeg;base64,' + base64Data;
        }
        this.rerender();
      },
      (error) => console.log(error)
    );
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.destroy();
      this.dtTrigger.next();
    });
  }
}

// let base64Data = this.allProducts[0].productImage;
// this.allProducts[0].productImage =
//   'data:image/jpeg;base64,' + base64Data;
// console.log(this.allProducts);
// console.log(typeof this.allProducts[0].productImage);
