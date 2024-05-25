package com.test.shop.admin.controller;

import com.test.shop.admin.controller.request.AdminLoginRequest;
import com.test.shop.admin.controller.request.CreateProductRequest;
import com.test.shop.admin.controller.request.OrderStateRequest;
import com.test.shop.admin.controller.response.CreateProductResponse;
import com.test.shop.admin.service.AdminService;
import com.test.shop.common.annotation.AdminAuthCheck;
import com.test.shop.order.controller.response.DetailOrderResponse;
import com.test.shop.order.controller.response.OrderItemResponse;
import com.test.shop.order.controller.response.OrderResponse;
import com.test.shop.order.service.OrderService;
import com.test.shop.product.controller.response.DetailProductResponse;
import com.test.shop.product.entity.Product;
import com.test.shop.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	private final AdminService adminService;
	private final ProductService productService;
	private final OrderService orderService;

		@PostMapping("/login")
		public ResponseEntity<Void> login(@RequestBody AdminLoginRequest adminLoginRequest, HttpSession session) {
			log.info("adminLoginRequest = {}", adminLoginRequest);
			adminService.login(adminLoginRequest.getId(), adminLoginRequest.getPassword());
			session.setAttribute("adminId", adminLoginRequest.getId());
			String sessionId = session.getId();
			return ResponseEntity.ok()
					.header("X-Auth-Token", sessionId)
					.build();
	}

	@GetMapping("/product/all")
	@AdminAuthCheck
	public ResponseEntity<Void> getAllProduct(HttpSession session) {
		log.info("test");
		String sessionId = session.getId();

		List<Product> products = productService.findAllProduct();

		return ResponseEntity.ok()
				.header("X-Auth-Token", sessionId)
				.build();
	}

	@PostMapping("/add/product")
	@AdminAuthCheck
	public ResponseEntity<CreateProductResponse> addProduct(@RequestBody CreateProductRequest createProductRequest) {
		Long id = adminService.addProduct(createProductRequest);
		return ResponseEntity.ok(new CreateProductResponse(id));
	}

	@PostMapping("/edit/product/{productId}")
	@AdminAuthCheck
	public ResponseEntity<Void> editProduct(@RequestBody CreateProductRequest createProductRequest, @PathVariable("productId") Long productId) {
		adminService.editProduct(createProductRequest, productId);
		return ResponseEntity.ok().build();
	}


	@GetMapping("/view/product/{page}/{size}")
	@AdminAuthCheck
	public ResponseEntity<List<DetailProductResponse>> viewProduct(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
		return ResponseEntity.ok(productService.viewPagingProducts(page - 1, size));
	}

	@GetMapping("/view/orders/{page}/{size}")
	@AdminAuthCheck
	public ResponseEntity<List<DetailOrderResponse>> viewOrders(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
		return ResponseEntity.ok(orderService.viewPagingOrders(page - 1, size));
	}

	@GetMapping("/view/details/items/{orderId}")
	@AdminAuthCheck
	public ResponseEntity<OrderResponse> viewOrders(@PathVariable("orderId") Long orderId) {
		return ResponseEntity.ok(orderService.viewOrderDetails(orderId));
	}


	@PostMapping("/change/state/{orderId}")
	@AdminAuthCheck
	public ResponseEntity<Void> changeOrderState(@PathVariable("orderId") Long orderId, @RequestBody OrderStateRequest orderStateRequest) {
		orderService.changeOrderState(orderId, orderStateRequest);
		return ResponseEntity.ok().build();
	}

}
