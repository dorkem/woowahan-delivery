package com.dorkem.food.store.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor()
public class StoreReviewStatus {
	// TODO: 임시로 0.0 설정
	@Getter
	@Column(name = "average_rating", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
	private Double averageRating = 0.0;

	@Getter
	@Column(name = "review_count", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
	private Integer reviewCount = 0;

	private StoreReviewStatus(Double averageRating, Integer reviewCount) {
		this.averageRating = averageRating;
		this.reviewCount = reviewCount;
	}

	public StoreReviewStatus addReview(int newRating) {
		double totalRating = (this.averageRating * this.reviewCount) + newRating;
		int newCount = this.reviewCount + 1;

		return new StoreReviewStatus(totalRating / newCount, newCount);
	}
}
