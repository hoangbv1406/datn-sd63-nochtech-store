// File: category.admin.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-category-admin',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './category.admin.component.html',
  styleUrl: './category.admin.component.scss'
})
export class CategoryAdminComponent {
}
