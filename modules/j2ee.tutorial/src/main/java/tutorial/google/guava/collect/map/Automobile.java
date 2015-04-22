package tutorial.google.guava.collect.map;

public class Automobile {
	private String make;
	private String model;
	private Integer milesPerGallon;
	
	public Automobile() {}	
	public Automobile(String make, String model, Integer milesPerGallon) {
		super();
		this.make=make;
		this.model=model;
		this.milesPerGallon=milesPerGallon;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make=make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model=model;
	}
	public Integer getMilesPerGallon() {
		return milesPerGallon;
	}
	public void setMilesPerGallon(Integer milesPerGallon) {
		this.milesPerGallon=milesPerGallon;
	}



}
