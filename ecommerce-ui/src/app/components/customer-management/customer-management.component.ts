import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-customer-management',
  templateUrl: './customer-management.component.html',
  styleUrls: ['./customer-management.component.css'],
})
export class CustomerManagementComponent
  implements OnInit, AfterViewInit, OnDestroy {
  constructor(private userService: UserService) {}

  @ViewChild(DataTableDirective, { static: false })
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: DataTables.Settings = {};
  customers: any;

  ngOnInit(): void {
    this.dtOptions = {
      lengthMenu: [
        [10, 25, 50, -1],
        [10, 25, 50, 'All'],
      ],
    };
    this.loadData();
  }

  loadData() {
    this.userService.getAllCustomers().subscribe(
      (data: any) => {
        this.customers = data;
        console.log(this.customers);
        this.rerender();
      },
      (error) => console.log(error)
    );
  }

  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  deleteCustomer(customerId) {
    this.userService.deleteCustomer(customerId).subscribe(
      (data) => {
        console.log(data);
        this.loadData();
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
