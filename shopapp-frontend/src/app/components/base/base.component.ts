// File: base.component.ts
import { inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location, DOCUMENT } from '@angular/common';

import { ToastService } from '../../services/toast.service';
import { CategoryService } from '../../services/category.service';
import { ProductService } from '../../services/product.service';
import { CouponService } from '../../services/coupon.service';
import { OrderService } from '../../services/order.service';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';
import { RoleService } from '../../services/role.service';
import { UserService } from '../../services/user.service';
import { TokenService } from '../../services/token.service';
import { PaymentService } from '../../services/payment.service';

export class BaseComponent {
    router: Router = inject(Router);
    activatedRoute: ActivatedRoute = inject(ActivatedRoute);
    location: Location = inject(Location);
    document: Document = inject(DOCUMENT);

    toastService: ToastService = inject(ToastService);
    categoryService: CategoryService = inject(CategoryService);
    productService: ProductService = inject(ProductService);
    couponService: CouponService = inject(CouponService);
    orderService: OrderService = inject(OrderService);
    cartService: CartService = inject(CartService);
    authService: AuthService = inject(AuthService);
    roleService: RoleService = inject(RoleService);
    userService: UserService = inject(UserService);
    tokenService: TokenService = inject(TokenService);
    paymentService: PaymentService = inject(PaymentService);
}
