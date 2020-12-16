package com.ecommerce.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class ConfirmedOrder{


	@Id
	@GeneratedValue
	private int confirmedOrderId;

	@OneToOne
	@JoinColumn(name = "customerId")
	private Customer customer;


	@OneToMany(mappedBy = "confirmedOrder", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ConfirmedOrderItem> orderItems;

	private double grandTotal;

	@Temporal(TemporalType.DATE)
	private Date orderDate;

	private boolean isFulfilled;
	
	@OneToOne
	@JoinColumn(name = "paymentId")
	private Payment payment;

	@Temporal(TemporalType.DATE)
	private Date fulfillmentDate;

	public int getConfirmedOrderId() {
		return confirmedOrderId;
	}

	public void setConfirmedOrderId(int confirmedOrderId) {
		this.confirmedOrderId = confirmedOrderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public List<ConfirmedOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<ConfirmedOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public boolean getIsFulfilled() {
		return isFulfilled;
	}

	public void setFulfilled(boolean isFulfilled) {
		this.isFulfilled = isFulfilled;
	}

	public Date getFulfillmentDate() {
		return fulfillmentDate;
	}

	public void setFulfillmentDate(Date fulfillmentDate) {
		this.fulfillmentDate = fulfillmentDate;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
