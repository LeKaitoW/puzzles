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
	private String[][] bestPosition = new String[6][6];
	private String[][] currentPosition = new String[6][6];
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

	public final static String[][] check = {
			{ "k", "b", "u", "a", "r", "o" },
			{ "o", "a", "k", "r", "b", "u" },
			{ "b", "r", "o", "u", "a", "k" },
			{ "u", "o", "r", "b", "k", "a" },	
			{ "r", "k", "a", "o", "u", "b" },
			{ "a", "u", "b", "k", "o", "r" }};

	private final static String[][] startPosition = new String[][] {
			{ "k", " ", " ", " ", " ", " " },
			{ "o", " ", " ", " ", "b", " " },
			{ "b", " ", " ", " ", "a", " " },
			{ "u", " ", "r", " ", "k", " " },
			{ "r", " ", "a", " ", " ", " " },
			{ "a", " ", "b", " ", " ", " " }};

	public Squareword(){
	}
	
	public String[][] getCurrentPosition(){
		return currentPosition;
	}

	private void setCurrentPosition(String[][] position) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++)
				currentPosition[i][j] = position[i][j];
		}
	}
	
	private void setBestPosition(String[][] position) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++)
				bestPosition[i][j] = position[i][j];
		}
	}
	
	private void setNewRow(String[] row, int index) {
		for (int i = 0; i < row.length; i++) {
			currentPosition[index][i] = row[i];
		}
		checkBest(index);
	}
	
	public void printPosition(String[][] position) {
		for (int i = 0; i < position.length; i++) {
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
	
	private void checkBest(int index){
		int conflicts = checkConflictNumber(currentPosition);
		if (conflicts <= minConflictsNumber){
			minConflictsNumber = conflicts;
			setBestPosition(currentPosition);
		}
	}

	private void generatePosition() {
		Random randomizer = new Random(System.nanoTime());
		setCurrentPosition(startPosition);
		for (String[] row : currentPosition) {
			for (int i = 0; i < row.length; i++) {
				if (!row[i].equals(" "))
					continue;
				int index = randomizer.nextInt(letters.size());
				while (Arrays.asList(row).contains(letters.get(index)))
					index = randomizer.nextInt(letters.size());
				row[i] = letters.get(index);
			}
		}
	}

	private int checkConflictNumber(String[][] position) {
		int conflictNum = 0;//index;
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
	
	private String[] getRow(int index){
		String[] newRow = new String[6];
		for (int i=0; i<6; i++){
			newRow[i] = currentPosition[index][i];
		}
		return newRow;
	}
	
	private String[] convertToPart(int index){
		String[] row = getRow(index);
		String[] part = null;
		switch(index){
		case 0:
			part = Arrays.copyOfRange(row, 1, 6);
			break;
		case 1:
		case 2:
			part = new String[4];
			part[0] = row[1];
			part[1] = row[2];
			part[2] = row[3];
			part[3] = row[5];
			break;
		case 3:
			part = new String[3];
			part[0] = row[1];
			part[1] = row[3];
			part[2] = row[5];
			break;
		case 4:
		case 5:
			part = new String[4];
			part[0] = row[1];
			part[1] = row[3];
			part[2] = row[4];
			part[3] = row[5];
			break;
		}
		return part;
	}

	private String[] convertToRow(String[] set, int index){
		String[] row = getRow(index);
		switch (index) {
		case 0:
			for (int i = 0; i < 5; i++)
				row[i + 1] = set[i];
			break;
		case 1:
		case 2:
			row[1] = set[0];
			row[2] = set[1];
			row[3] = set[2];
			row[5] = set[3];
			break;
		case 3:
			row[1] = set[0];
			row[3] = set[1];
			row[5] = set[2];
			break;
		case 4:
		case 5:
			row[1] = set[0];
			row[3] = set[1];
			row[4] = set[2];
			row[5] = set[3];
			break;
		}
		return row;
	}

	public void generateAllRows(int index) {
		HashSet<String[]> hash = new HashSet<String[]>();
		permute(Arrays.asList(convertToPart(index)), 0, index, hash);
		for (String[] row : hash){
			setNewRow(row, index);
		}
		hash.clear();
	}
	
	public void solutionWithProbabilty(int index){
		List<Row> rows = new ArrayList<>();
		permuteWithProbability(Arrays.asList(convertToPart(index)), 0, index, rows);
		
		Map<Integer, List<Integer>> rates = new HashMap<>();
		for (Row row : rows){
			if (rates.containsKey(row.getRate()))
				rates.get(row.getRate()).add(rows.indexOf(row));
			else{
				List<Integer> indexes = new ArrayList<Integer>();
				indexes.add(rows.indexOf(row));
				rates.put(row.getRate(), indexes);
			}
		}
		int randomRate = (int) (Math.log(1/Math.random())/Math.log(2));
		int delta = Integer.MAX_VALUE;
		List<Integer> currentRows = new ArrayList<>();
		for (int rate : rates.keySet()){
			if (Math.abs(rate - randomRate) < delta){
				delta = Math.abs(rate - randomRate);
				currentRows = rates.get(rate);
			}
		}
		Random randomizer = new Random();
		int randomIndex = randomizer.nextInt(currentRows.size());
		String[] newRow = rows.get(currentRows.get(randomIndex)).getRow();
		setNewRow(newRow, index);
	}

	private void permuteWithProbability(List<String> arr, int k, int index, List<Row> rows) {
		for (int i = k; i < arr.size(); i++) {
			java.util.Collections.swap(arr, i, k);
			permuteWithProbability(arr, k + 1, index, rows);
			java.util.Collections.swap(arr, k, i);
		}
		if (k == arr.size() - 1) {
			String[] row = convertToRow((String[]) arr.toArray(), index);
			setNewRow(row, index);
			rows.add(new Row(checkConflictNumber(currentPosition), row));
		}
	}

	private void permute(List<String> arr, int k, int index, HashSet<String[]> hash) {
		for (int i = k; i < arr.size(); i++) {
			java.util.Collections.swap(arr, i, k);
			permute(arr, k + 1, index, hash);
			java.util.Collections.swap(arr, k, i);
		}
		if (k == arr.size() - 1) {
			hash.add(convertToRow((String[]) arr.toArray(), index));
		}
	}

	private boolean check(int index){
		if (Arrays.equals(currentPosition[index], check[index])){
				System.out.println("yep! " + index);
				return true;
		}
		return false;
	}

	public boolean haveSolution() {
		if (minConflictsNumber > 0) {
			System.out.println(minConflictsNumber);
			return false;
		}
		System.out.println("Solution:");
		printPosition(bestPosition);
		return true;
	}
	
	public void start(){
		minConflictsNumber = Integer.MAX_VALUE;
		generatePosition();
		//System.out.println("new");
		//printPosition(currentPosition);
		//checkBest(0);
	}
}
