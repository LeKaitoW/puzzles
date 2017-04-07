package squareword;

public class MainClass {

	public static void main(String[] args) {
		Squareword squareword;
		squareword = new Squareword();
		int i = 0;
		//while (!squareword.haveSolution()) {
			for (int j = 0; j < 6; j++) {
				while (!squareword.noMoreShuffle(j)) {
					squareword.generateNewRow(j);
					squareword.checkBest();
					i++;
				}
			//}
		}
		System.out.println("current = ");
		squareword.printPosition(squareword.getCurrentPosition());
		System.out.println("best = ");
		squareword.printPosition(squareword.getBestPosition());
		System.out.println(squareword.checkConflictNumber(squareword.getBestPosition()));
		System.out.println("minCon = " + squareword.getMin());
		System.out.println("iterations = " + i);
	}

}
