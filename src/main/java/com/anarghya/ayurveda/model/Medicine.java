package com.anarghya.ayurveda.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*@Entity: defines that said class is an entity and will be mapped to a database table.*/
@Entity
/*
 * @Table: allows us to specify the details of the table that will be used to
 * persist the entity in the database.
 */
@Table(name = "medicines")
public class Medicine {

	/* @Id: specifies the primary key of an entity. */
	@Id
	/*
	 * specifies that the primary key values for User entities should be generated
	 * using an identity column in the database.
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	
	private Long id;
	@Column(unique = true, nullable = false)
	private String name;
	private String mfdDate;
	private String expiryDate;
	private int quantity;
	private float cost;
	private String company;

	private String category;
	private String description;
	private String formula;
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDate medicineCreatedDate;
	@Column(insertable = false)
	@UpdateTimestamp
	private LocalDate medicineUpdatedDate;
	@Column(unique = true, nullable = false)
	private String batchCode;
	
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

	public Medicine(Long id, String name, String mfdDate, String expiryDate, int quantity, float cost, String company,
			String category, String description, String formula, String batchCode, Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.mfdDate = mfdDate;
		this.expiryDate = expiryDate;
		this.quantity = quantity;
		this.cost = cost;
		this.company = company;
		this.category = category;
		this.description = description;
		this.formula = formula;
		this.batchCode = batchCode;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMfdDate() {
		return mfdDate;
	}

	public void setMfdDate(String mfdDate) {
		this.mfdDate = mfdDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Medicine() {

	}

}
