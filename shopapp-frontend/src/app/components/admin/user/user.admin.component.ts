// File: user.admin.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user.admin',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './user.admin.component.html',
  styleUrl: './user.admin.component.scss',
})
export class UserAdminComponent {
}
