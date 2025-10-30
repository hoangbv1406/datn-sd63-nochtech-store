// File: admin.guard.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard {
  constructor() { }
  canActivate() { }
}
