package com.progresssoft.datawarehouse.entity;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;

/**
 * @author Raghavendra
 *
 */
@MappedSuperclass
@IdClass(MyKey.class)
public class DealModel {

	
	@Id
	private String id;
	@Column(name = "from_currency_iso_code")
	private String fromCurrency;
	@Column(name = "to_currency_iso_code")
	private String toCurrency;
	@Column(name = "date")
	private String dealDate;
	@Column(name = "deal_amount")
	private String amount;
	
	@Id
	@Column(name = "file_name")
	private String fileName;

	public DealModel() {
	}

	public DealModel(String id) {
		this.id = id;
	}
	
	/**
	 * @param id
	 * @param fromCurrency
	 * @param toCurrency
	 * @param dealDate
	 * @param amount
	 * @param fileName
	 */
	public DealModel(String id, String fromCurrency, String toCurrency, String dealDate, String amount,
			String fileName) {
		super();
		this.id = id;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.dealDate = dealDate;
		this.amount = amount;
		this.fileName = fileName;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	
	/**
	 * @return the fromCurrency
	 */
	public String getFromCurrency() {
		return fromCurrency;
	}

	/**
	 * @param fromCurrency the fromCurrency to set
	 */
	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	/**
	 * @return the toCurrency
	 */
	public String getToCurrency() {
		return toCurrency;
	}

	/**
	 * @param toCurrency the toCurrency to set
	 */
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	/**
	 * @return the dealDate
	 */
	public String getDealDate() {
		return dealDate;
	}

	/**
	 * @param dealDate the dealDate to set
	 */
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


}
