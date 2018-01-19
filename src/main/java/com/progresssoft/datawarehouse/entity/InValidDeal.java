package com.progresssoft.datawarehouse.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author raghavendra
 *
 */
@Entity
@Table(name = "invalid_deal")
public class InValidDeal extends  DealModel implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
}
