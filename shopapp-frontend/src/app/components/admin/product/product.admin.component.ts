// File: product.admin.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-product-admin',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './product.admin.component.html',
  styleUrl: './product.admin.component.scss'
})
export class ProductAdminComponent {
}
