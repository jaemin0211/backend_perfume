package com.test.shop.like.controller.response;

import lombok.Data;

@Data
public class LikeResponse {

	private LikeStatus likeStatus;

	public static LikeResponse of(LikeStatus likeStatus) {
		LikeResponse likeResponse = new LikeResponse();
		likeResponse.likeStatus = likeStatus;
		return likeResponse;
	}
}
