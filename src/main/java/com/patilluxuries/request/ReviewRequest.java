package com.patilluxuries.request;


public class ReviewRequest {
	
	private Long prodcutId;
	private String review;
	public ReviewRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReviewRequest(Long prodcutId, String review) {
		super();
		this.prodcutId = prodcutId;
		this.review = review;
	}
	public Long getProdcutId() {
		return prodcutId;
	}
	public void setProdcutId(Long prodcutId) {
		this.prodcutId = prodcutId;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	

}
