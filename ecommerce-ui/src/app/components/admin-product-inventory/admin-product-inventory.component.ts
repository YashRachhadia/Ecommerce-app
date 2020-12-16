import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { ProductService } from 'src/app/services/product.service';
import { ProductDetailsComponent } from '../product-details/product-details.component';

@Component({
  selector: 'app-admin-product-inventory',
  templateUrl: './admin-product-inventory.component.html',
  styleUrls: ['./admin-product-inventory.component.css'],
})
export class AdminProductInventoryComponent
  implements OnInit, AfterViewInit, OnDestroy {
  constructor(
    private productService: ProductService,
    public viewProductBox: MatDialog,
    private router: Router
  ) {}

  allProducts: any;

  @ViewChild(DataTableDirective, { static: false })
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: DataTables.Settings = {};

  ngOnInit(): void {
    this.dtOptions = {
      lengthMenu: [
        [10, 25, 50, -1],
        [10, 25, 50, 'All'],
      ],
    };
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

  updateProduct(product) {
    this.router.navigate(['admin/productInventory/update', product.productId]);
  }

  deleteProduct(product) {
    this.productService.deleteProduct(product.productId).subscribe(
      (res) => {
        console.log(res);
        this.loadData();
      },
      (error) => console.log(error)
    );
  }
}
