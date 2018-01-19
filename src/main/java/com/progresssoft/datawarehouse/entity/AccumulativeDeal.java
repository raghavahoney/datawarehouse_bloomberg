package com.progresssoft.datawarehouse.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accumulative_deals")
public class AccumulativeDeal {
	
	@Id
	@Column(name="CurrencyISOCode")
	private String orderingCurrency;
	
	@Column(name = "CountOfDeals")
	private BigInteger count ;

	public String getOrderingCurrency() {
		return orderingCurrency;
	}
	/**
	 * @param oderingCurrency the oderingCurrency to set
	 */
	public void setOrderingCurrency(String orderingCurrency) {
		this.orderingCurrency = orderingCurrency;
	}
	/**
	 * @return the count
	 */
	
	public BigInteger getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(BigInteger count) {
		this.count = count;
	}
	
	

}
