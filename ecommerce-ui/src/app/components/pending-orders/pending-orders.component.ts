import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-pending-orders',
  templateUrl: './pending-orders.component.html',
  styleUrls: ['./pending-orders.component.css'],
})
export class PendingOrdersComponent implements OnInit {
  constructor(private orderService: OrderService) {}

  pendingOrders: any;
  ngOnInit(): void {
    this.orderService.getPendingOrders().subscribe(
      (res) => {
        console.log(res);
        this.pendingOrders = res;
      },
      (error) => console.log(error)
    );
  }

  setFulfilled(confirmedOrderId) {
    this.orderService.setOrderFulfilled(confirmedOrderId).subscribe(
      (res) => {
        console.log(res);
        this.ngOnInit();
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
