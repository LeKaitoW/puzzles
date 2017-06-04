package squareword;

public class MainClass {

	public static void main(String[] args) {
		Squareword squareword = new Squareword();
		int i = 0;
		while (true) {
			for (int j = 0; j < 6; j++)
				squareword.solutionWithProbabilty(j, true);
			if (squareword.haveSolution())
				break;
			squareword.start();
			System.out.println(i);
			i++;
		}
		System.out.println("iterations = " + i);
		System.out.println("Solution:");
		squareword.printPosition(squareword.getBestPosition());
	}
}
