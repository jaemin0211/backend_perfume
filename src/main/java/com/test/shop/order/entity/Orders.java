package com.test.shop.order.entity;

import com.test.shop.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	private LocalDateTime orderDate;
	@Enumerated(value = EnumType.STRING)
	private OrderState orderState;
	private Integer totalAmount;
	private String receiverName;
	private String receiverPhone;
	private String shippingAddress;

	@ManyToOne
	@JoinColumn(name = "memberNo")
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItems> orderItems = new ArrayList<>();

	public List<OrderItems> getOrderItems() {
		return orderItems;
	}

	// OrderItem 리스트에 OrderItem을 추가하는 메소드
	public void addOrderItem(OrderItems orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void changeOrderState(OrderState orderState) {
		this.orderState = orderState;
	}


}