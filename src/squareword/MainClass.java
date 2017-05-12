package squareword;

public class MainClass {

	public static void main(String[] args) {
		Squareword squareword;
		int min = Integer.MAX_VALUE;
		squareword = new Squareword();
		int i = 0;
		while (!squareword.haveSolution()) {
			//squareword.start();
			for (int j = 0; j < 6; j++) {
				//squareword.generateAllRows(j);
				squareword.solutionWithProbabilty(j);
			}
			System.out.println(i);
			if (squareword.getMin() < min)
				min = squareword.getMin();
			i++;
			System.out.println("glob min " + min);
		}
		System.out.println("iterations = " + i);
		System.out.println(squareword.seed);
	}

}
