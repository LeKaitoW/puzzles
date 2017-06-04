package squareword;

public class Row {
	private int rate;
	private String[] row;

	public Row(int rate, String[] row) {
		this.rate = rate;
		this.row = row;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getRate() {
		return rate;
	}

	public String[] getRow() {
		return row;
	}
}
