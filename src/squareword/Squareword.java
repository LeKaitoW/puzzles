package squareword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.net.ssl.SSLEngineResult.Status;

public class Squareword {
	private int minConflictsNumber = Integer.MAX_VALUE;
	private String[][] bestPosition;
	private static HashSet<String[]> hashZero = new HashSet<>();
	private static HashSet<String[]> hashOne = new HashSet<>();
	private static HashSet<String[]> hashTwo = new HashSet<>();
	private static HashSet<String[]> hashThree = new HashSet<>();
	private static HashSet<String[]> hashFour = new HashSet<>();
	private static HashSet<String[]> hashFive = new HashSet<>();
	private static Map<Integer, HashSet<String[]>> hashSets = new HashMap<Integer, HashSet<String[]>>(){{
		put(0, hashZero);
		put(1, hashOne);
		put(2, hashTwo);
		put(3, hashThree);
		put(4, hashFour);
		put(5, hashFive);
	}
	};
	
	private final static ArrayList<String> letters = new ArrayList<String>() {
		{
			add("k");
			add("o");
			add("b");
			add("u");
			add("r");
			add("a");
		}
	};

	private final static String[][] startPosition = new String[][] {
			{ "k", " ", " ", " ", " ", " " },
			{ "o", " ", " ", " ", "b", " " },
			{ "b", " ", " ", " ", "a", " " },
			{ "u", " ", "r", " ", "k", " " },
			{ "r", " ", "a", " ", " ", " " },
			{ "a", " ", "b", " ", " ", " " } };

	public Squareword(){
		String[][] positiion = generatePosition();
		setBest(positiion, checkConflictNumber(positiion));
		System.out.println("start:");
		printPosition(bestPosition);
	}
	
	public void printPosition(String[][] position) {
		for (int i=0; i< position.length; i++) {
			for (int j = 0; j < position.length; j++) {
				System.out.print(position[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public String[][] getBestPosition(){
		return bestPosition;
	}
	
	public int getMin(){
		return minConflictsNumber;
	}
	
	public void setBest(String[][] position, int conflicts){
		if (conflicts < minConflictsNumber){
			this.minConflictsNumber = conflicts;
			this.bestPosition = position;
			System.out.println("best:");
			printPosition(this.bestPosition);
			System.out.println(this.minConflictsNumber);
		}
	}

	public String[][] generatePosition() {
		Random randomizer = new Random();
		String[][] newPosition = startPosition;
		for (String[] row : newPosition) {
			for (int i = 0; i < row.length; i++) {
				if (!row[i].equals(" "))
					continue;
				int index = randomizer.nextInt(letters.size());
				while (Arrays.asList(row).contains(letters.get(index)))
					index = randomizer.nextInt(letters.size());
				row[i] = letters.get(index);
			}
		}
		checkConflictNumber(newPosition);
		return newPosition;
	}

	public int checkConflictNumber(String[][] position) {
		int conflictNum = 0;
		List<String> buf = new ArrayList<String>();
		for (int i = 0; i < position.length; i++) {
			buf.clear();
			for (int j = 0; j < position.length; j++) {
				buf.add(position[j][i].toString());
			}
			Set<String> hashSet = new HashSet<String>(buf);
			conflictNum += (buf.size() - hashSet.size());
		}

		buf.clear();
		for (int i = 0; i < position.length; i++) {
			buf.add(position[i][i].toString());
		}
		Set<String> hashSet = new HashSet<String>(buf);
		conflictNum += (buf.size() - hashSet.size());

		buf.clear();
		for (int i = 0; i < position.length; i++) {
			buf.add(position[i][position.length - i - 1].toString());
		}
		hashSet = new HashSet<String>(buf);
		conflictNum += (buf.size() - hashSet.size());

		return conflictNum;
	}
	
	public String[] generateNewRow(String[] row, int index){
		List<String> newRowList = Arrays.asList(row);
		List<String> part = new ArrayList<>();
		switch (index){
		case 0:
			part = newRowList.subList(1, 6);
			break;
		case 1:
		case 2:
			part.add(row[1]);
			part.add(row[2]);
			part.add(row[3]);
			part.add(row[5]);
			Collections.shuffle(part);
			row[1] = part.get(0);
			row[2] = part.get(1);
			row[3] = part.get(2);
			row[5] = part.get(3);
			break;
		case 3:
			part.add(row[1]);
			part.add(row[3]);
			part.add(row[5]);
			row[1] = part.get(0);
			row[3] = part.get(1);
			row[5] = part.get(2);
			break;
		case 4:
		case 5:
			part.add(row[1]);
			part.add(row[3]);
			part.add(row[4]);
			part.add(row[5]);
			row[1] = part.get(0);
			row[3] = part.get(1);
			row[4] = part.get(2);
			row[5] = part.get(3);
			break;
		}
		Collections.shuffle(part);
		String[] newRow = newRowList.toArray(new String[newRowList.size()]);
		return newRow;
	}
	
	public boolean haveSolution(){
		if (minConflictsNumber != 0)
			return false;
		System.out.println("Solution:");
		printPosition(bestPosition);
		return true;
	}
	
	public boolean noMoreShuffle(String[] row, int index){
		HashSet<String[]> hash = hashSets.get(index);
		hash.add(row);
		switch (index){
		case 0:
			return hash.size() >= 121;
		case 1:
		case 2:
		case 4:
		case 5:
			return hash.size() >= 25;
		case 3:
			return hash.size() >= 7;
		}
		return false;
	}
}
