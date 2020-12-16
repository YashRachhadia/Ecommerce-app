import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminProductInventoryComponent } from './admin-product-inventory.component';

describe('AdminProductInventoryComponent', () => {
  let component: AdminProductInventoryComponent;
  let fixture: ComponentFixture<AdminProductInventoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminProductInventoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminProductInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
