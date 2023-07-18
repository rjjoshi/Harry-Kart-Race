package se.atg.harrykart.dto;

public class PositionCalculator {
	private String horseName;
	private int currentSpeed;
	private Double totalTime;

	public PositionCalculator(String horseName, int currentSpeed, Double totalTime) {
		super();
		this.horseName = horseName;
		this.currentSpeed = currentSpeed;
		this.totalTime = totalTime;
	}

	public String getHorseName() {
		return horseName;
	}

	public void setHorseName(String horseName) {
		this.horseName = horseName;
	}

	public int getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(int currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}

}
