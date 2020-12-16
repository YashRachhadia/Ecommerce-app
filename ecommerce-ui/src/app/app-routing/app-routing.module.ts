import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../components/login/login.component';
import { RegisterComponent } from '../components/register/register.component';
import { HomeComponent } from '../components/home/home.component';
import { ProductListComponent } from '../components/product-list/product-list.component';
import { AboutUsComponent } from '../components/about-us/about-us.component';
import { AdminComponent } from '../components/admin/admin.component';
import { CustomerManagementComponent } from '../components/customer-management/customer-management.component';
import { AddProductComponent } from '../components/add-product/add-product.component';
import { CartComponent } from '../components/cart/cart.component';
import { CheckoutComponent } from '../components/checkout/checkout.component';
import { PaymentComponent } from '../components/payment/payment.component';
import { AdminProductInventoryComponent } from '../components/admin-product-inventory/admin-product-inventory.component';
import { UpdateProductComponent } from '../components/update-product/update-product.component';
import { OrdersHistoryComponent } from '../components/orders-history/orders-history.component';
import { PendingOrdersComponent } from '../components/pending-orders/pending-orders.component';
import { AuthGuard } from '../shared/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'productList', component: ProductListComponent },
  { path: 'aboutUs', component: AboutUsComponent },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN',
    },
  },
  {
    path: 'admin/customerManagement',
    component: CustomerManagementComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN',
    },
  },
  {
    path: 'admin/addProduct',
    component: AddProductComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN',
    },
  },
  {
    path: 'customer/cart',
    component: CartComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_USER',
    },
  },
  {
    path: 'customer/order/checkout/:cartId',
    component: CheckoutComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_USER',
    },
  },
  {
    path: 'customer/order/payment/:cartId',
    component: PaymentComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_USER',
    },
  },
  {
    path: 'admin/productInventory',
    component: AdminProductInventoryComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN',
    },
  },
  {
    path: 'admin/productInventory/update/:productId',
    component: UpdateProductComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN',
    },
  },
  {
    path: 'admin/pendingOrders',
    component: PendingOrdersComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN',
    },
  },
  {
    path: 'admin/ordersHistory',
    component: OrdersHistoryComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN',
    },
  },
];

@NgModule({
  declarations: [],
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
