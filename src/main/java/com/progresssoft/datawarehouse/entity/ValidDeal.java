package com.progresssoft.datawarehouse.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author raghavendra
 *
 */
@Entity
@Table(name = "valid_deal")
public class ValidDeal extends  DealModel implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;

}
