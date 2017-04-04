package squareword;

public class MainClass {

	public static void main(String[] args) {
		Squareword squareword;
		squareword = new Squareword();
		String[][] position = squareword.getBestPosition();
		int i = 0;
		for (int j = 0; j < 6; j++) {
			while (!squareword.noMoreShuffle(position[j], j)) {
				position[j] = squareword.generateNewRow(position[j], j);
				squareword.setBest(position, squareword.checkConflictNumber(position));
				i++;
			}
			position = squareword.getBestPosition();
		}
		position = squareword.getBestPosition();
		squareword.printPosition(position);
		System.out.println(squareword.checkConflictNumber(position));
		System.out.println(squareword.getMin());
		squareword.printPosition(squareword.getBestPosition());
		System.out.println(i);
	}

}
