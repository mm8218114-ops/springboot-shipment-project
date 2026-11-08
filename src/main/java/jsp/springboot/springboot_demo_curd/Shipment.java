package jsp.springboot.springboot_demo_curd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Shipment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private Integer trackingNumber;
	private String origin;
	private String destination;
	private enum status{
		delivered,shipped,out_of_delivery;
	}
	private enum carrier {
		Bluedart, FedEx, Delivery;
	}
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(int trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	@Override
	public String toString() {
		return "Shipment [id=" + id + ", trackingNumber=" + trackingNumber + ", origin=" + origin + ", destination="
				+ destination + "]";
	}
	
	
	
	
	
	
	
	
	
}
