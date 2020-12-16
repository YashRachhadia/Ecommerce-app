import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-orders-history',
  templateUrl: './orders-history.component.html',
  styleUrls: ['./orders-history.component.css'],
})
export class OrdersHistoryComponent implements OnInit {
  constructor(private orderService: OrderService) {}

  allOrders: any;
  ngOnInit(): void {
    this.orderService.getOrdersHistory().subscribe(
      (res) => (this.allOrders = res),
      (error) => console.log(error)
    );
  }
}
