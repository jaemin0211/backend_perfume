package com.test.shop.order.service;

import com.test.shop.admin.controller.request.OrderStateRequest;
import com.test.shop.cart.entity.Cart;
import com.test.shop.cart.service.CartService;
import com.test.shop.common.exception.ErrorCode;
import com.test.shop.common.exception.GlobalException;
import com.test.shop.member.entity.Member;
import com.test.shop.order.controller.request.OrderRequest;
import com.test.shop.order.controller.response.DetailOrderResponse;
import com.test.shop.order.controller.response.OrderItemResponse;
import com.test.shop.order.controller.response.OrderResponse;
import com.test.shop.order.entity.OrderItems;
import com.test.shop.order.entity.OrderState;
import com.test.shop.order.entity.Orders;
import com.test.shop.order.repository.OrderItemRepository;
import com.test.shop.order.repository.OrderRepository;
import com.test.shop.product.entity.Product;
import com.test.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final CartService cartService;
	private final ProductService productService;
	private final OrderItemRepository orderItemRepository;

	@Transactional
	public Long orderCart(Long memberNo, OrderRequest orderRequest) {
		//카트조회
		List<Cart> findCarts = cartService.findByCartIdAndMemberId(memberNo);

		if (findCarts.isEmpty()) {
			throw new GlobalException(ErrorCode.EMPTY_CART);
		}

		Map<Product, Long> productCountMap = findCarts.stream()
				.map(Cart::getProduct)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		productCountMap.forEach((pro, qty) -> {
			if (pro.getQty() < qty) {
				throw new GlobalException(ErrorCode.OUT_OF_STOCK, String.format("부족 상품 id %d 상품명 %s", pro.getProductId(), pro.getName()));
			}
		});

		//상품 가격 합산
		BigDecimal totalPrice = productCountMap.entrySet().stream()
				.map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		//상품 order 생성
		Orders saveOrder = orderRepository.save(Orders.builder()
				.orderDate(LocalDateTime.now())
				.orderState(OrderState.Pending)
				.shippingAddress(orderRequest.getShippingAddress())
				.receiverName(orderRequest.getReceiverName())
				.receiverPhone(orderRequest.getReceiverPhone())
				.totalAmount(totalPrice.intValue())
				.member(Member.ofNo(memberNo))
				.build());


		//상품 items 저장
		List<OrderItems> list = productCountMap.entrySet().stream()
				.map(i -> OrderItems.builder()
						.orderDate(LocalDateTime.now())
						.product(i.getKey())
						.quantity(Math.toIntExact(i.getValue()))
						.order(saveOrder)
						.build())
				.toList();

		orderItemRepository.saveAll(list);

		List<Product> product = productCountMap.entrySet().stream()
				.peek(i -> i.getKey().minusQty(Math.toIntExact(i.getValue())))
				.map(Map.Entry::getKey)
				.toList();

		//qty update
		productService.productSaveAll(product);

		return saveOrder.getOrderId();
	}

	@Transactional
	public Long orderProduct(Long memberNo, OrderRequest orderRequest, Long productId) {
		List<Product> findProduct = productService.findAllProduct(Collections.singletonList(productId));
		Product product = findProduct.getFirst();
		Orders order = Orders.builder()
				.orderDate(LocalDateTime.now())
				.orderState(OrderState.Pending)
				.receiverPhone(orderRequest.getReceiverPhone())
				.shippingAddress(orderRequest.getShippingAddress())
				.receiverName(orderRequest.getReceiverName())
				.totalAmount(product.getPrice().intValue() * orderRequest.getQty())
				.member(Member.ofNo(memberNo))
				.build();

		Orders saveOrder = orderRepository.save(order);

		OrderItems orderItems = OrderItems.builder()
				.product(product)
				.orderDate(LocalDateTime.now())
				.quantity(orderRequest.getQty())
				.order(order)
				.build();
		orderItemRepository.save(orderItems);

		product.minusQty(orderRequest.getQty());

		productService.productSaveAll(Collections.singletonList(product));

		return saveOrder.getOrderId();
	}

	@Transactional(readOnly = true)
	public List<OrderResponse> findMyOrder(Long memberNo) {
		List<Orders> orders = orderRepository.findAllByMemberNoWithOrderItemsAndProduct(memberNo);
		return orders.stream().map(order -> {
			List<OrderItemResponse> itemDtos = order.getOrderItems().stream().map(item -> new OrderItemResponse(
					item.getProduct().getProductId(),
					item.getProduct().getPrice(),
					item.getQuantity(),
					item.getProduct().getName()
			)).toList();

			return OrderResponse.builder()
					.orderId(order.getOrderId())
					.orderState(order.getOrderState().name())
					.totalAmount(order.getTotalAmount())
					.items(itemDtos)
					.build();
		}).toList();

	}

	public List<DetailOrderResponse> viewPagingOrders(Integer page, Integer size) {
		if (page < 0 || size <= 0) {
			throw new GlobalException(ErrorCode.INVALID_REQUEST);
		}
		return orderRepository.findAll(PageRequest.of(page, size))
				.map(DetailOrderResponse::of)
				.toList();
	}

	public OrderResponse viewOrderDetails(Long orderId) {
		Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new GlobalException(ErrorCode.NOT_ORDER));
		List<OrderItems> orderItems = orderItemRepository.findByOrder(orders);

		return OrderResponse.of(orders, orderItems);
	}

	public void changeOrderState(Long orderId, OrderStateRequest orderStateRequest) {
		Orders orders = orderRepository.findById(orderId)
				.orElseThrow(() -> new GlobalException(ErrorCode.NOT_ORDER));
		OrderState orderState = OrderState.getOrderState(orderStateRequest.getOrderState());
		orders.changeOrderState(orderState);
		orderRepository.save(orders);
	}
}
