import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ProductListComponent } from './components/product-list/product-list.component';
import { DataTablesModule } from 'angular-datatables';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { MatCardModule } from '@angular/material/card';
import { PaymentComponent } from './components/payment/payment.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { UserService } from './services/user.service';
import { AdminComponent } from './components/admin/admin.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { CartComponent } from './components/cart/cart.component';
import { CustomerManagementComponent } from './components/customer-management/customer-management.component';
import { ProductService } from './services/product.service';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { AdminProductInventoryComponent } from './components/admin-product-inventory/admin-product-inventory.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { CartService } from './services/cart.service';
import { OrdersHistoryComponent } from './components/orders-history/orders-history.component';
import { PendingOrdersComponent } from './components/pending-orders/pending-orders.component';
import { OrderService } from './services/order.service';
import { HttpInterceptorService } from './services/http-interceptor.service';
import { AuthenticationService } from './services/authentication.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    RegisterComponent,
    LoginComponent,
    ProductListComponent,
    AboutUsComponent,
    PaymentComponent,
    CheckoutComponent,
    AdminComponent,
    AddProductComponent,
    CartComponent,
    CustomerManagementComponent,
    ProductDetailsComponent,
    AdminProductInventoryComponent,
    UpdateProductComponent,
    OrdersHistoryComponent,
    PendingOrdersComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    DataTablesModule,
    MatCardModule,
    MatButtonModule,
    MatDialogModule,
  ],
  providers: [
    UserService,
    ProductService,
    CartService,
    OrderService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    },
    AuthenticationService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
