package de.florianmarsch;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class PricePoint {

	@Id
	private String id = UUID.randomUUID().toString();
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date date = new Date();
	
	@Column
	private int sunday = 0;
	
	@Column
	private int saturday = 0;
	
	@Column
	private int weekend = 0;
	
	@Column
	private int vip = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSunday() {
		return sunday;
	}

	public void setSunday(int sunday) {
		this.sunday = sunday;
	}

	public int getSaturday() {
		return saturday;
	}

	public void setSaturday(int saturday) {
		this.saturday = saturday;
	}

	public int getWeekend() {
		return weekend;
	}

	public void setWeekend(int weekend) {
		this.weekend = weekend;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PricePoint other = (PricePoint) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PricePoint [id=" + id + ", date=" + date + ", sunday=" + sunday + ", saturday=" + saturday
				+ ", weekend=" + weekend + ", vip=" + vip + "]";
	}
	
	
	
}
