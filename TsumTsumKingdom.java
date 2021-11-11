/* Tsum Tsum Kingdom multiplayer console game
 * Created June 2019
 */

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TsumTsumKingdom {
	
	static Scanner in = new Scanner(System.in);
	
	static int numPlayers = 0;
	static int numWorkers = 0;
	static int numInterns = 0;
	
	static int workersLeft = 0;
	static int internsLeft = 0;
	
	static String[] players = new String[10];
	static String[] workers = new String[15];
	static String[] interns = new String[25];
	
	static int[] playerCash = new int[10];
	static int[] workerCash = new int[15];
	static int[] internCash = new int[25];
	
	static String[] rides = {"Skyway", "Leagues", "Pirates", "Tiki", "Jungle", "Muppet", "Movie", "Hollywood", "Everest", "Dinosaur", "Safaris", "Grizzly", "Screamin", "Toy", "Test", "Mission", "Soarin", "King", "Dumbo", "It's", "Mountain", "Matterhorn"};
	
	static int[][] baseValuePerRide = {{2, 0, 2, 4, 4, 2}, {4, 1, 3, 2, 3, 4}, 
			{2, 0, 3, 7, 2, 5}, {3, 2, 1, 1, 4, 5}, {2, 0, 5, 9, 0, 3}, {4, 5, 2, 1, 4, 5}, 
			{3, 6, 1, 1, 3, 4}, {8, 5, 7, 8, 10, 8}, {4, 0, 9, 15, 8, 10}, {7, 6, 6, 5, 8, 10}, 
			{5, 0, 12, 18, 7, 8}, {3, 0, 10, 8, 3, 2}, {2, 0, 5, 6, 10, 4}, {10, 8, 10, 6, 12, 15}, 
			{3, 0, 12, 15, 8, 5}, {6, 3, 4, 5, 8, 6}, {10, 15, 10, 9, 10, 12}, {2, 0, 4, 7, 12, 10}, 
			{5, 0, 15, 18, 0, 12}, {15, 12, 15, 13, 18, 16}, {5, 4, 15, 13, 16, 18}, {4, 0, 12, 15, 12, 14}};
	//Each sub-array is a ride
	
	static int[][] workerValuePerRide = {{2, 0, 3, 6, 6, 3}, {6, 1, 3, 2, 3, 8}, 
			{2, 0, 3, 6, 2, 5}, {3, 2, 1, 1, 3, 4}, {3, 0, 7, 12, 0, 4}, {4, 5, 3, 2, 5, 6}, 
			{3, 6, 1, 0, 2, 4}, {6, 4, 6, 5, 8, 5}, {3, 0, 5, 8, 4, 6}, {6, 4, 4, 4, 6, 8}, 
			{7, 0, 15, 20, 7, 9}, {3, 0, 8, 7, 3, 1}, {3, 0, 5, 7, 12, 4}, {9, 8, 10, 5, 13, 15}, 
			{3, 0, 15, 20, 10, 7}, {6, 4, 4, 4, 10, 8}, {7, 15, 8, 5, 8, 10}, {3, 0, 5, 8, 15, 12}, 
			{3, 0, 16, 20, 0, 12}, {12, 10, 12, 10, 18, 15}, {5, 5, 22, 19, 25, 30}, {5, 0, 15, 20, 15, 20}};
	
	static int[][] internValuePerRide = {{1, 0, 1, 3, 3, 1}, {2, 0, 2, 1, 2, 2}, 
			{1, 0, 1, 3, 1, 2}, {1, 1, 0, 1, 2, 2}, {2, 0, 3, 4, 0, 1}, {2, 2, 1, 0, 1, 3}, 
			{2, 3, 1, 0, 1, 2}, {3, 2, 2, 2, 4, 3}, {3, 0, 3, 3, 2, 2}, {4, 4, 2, 2, 3, 4}, 
			{3, 0, 4, 5, 2, 2}, {2, 0, 3, 2, 1, 1}, {2, 0, 2, 3, 4, 2}, {3, 4, 2, 1, 3, 5}, 
			{3, 0, 5, 5, 3, 2}, {3, 2, 2, 2, 2, 2}, {3, 5, 2, 1, 2, 3}, {3, 0, 2, 3, 4, 3}, 
			{5, 0, 3, 3, 0, 3}, {5, 5, 3, 3, 4, 3}, {3, 3, 3, 3, 4, 4}, {5, 0, 5, 6, 4, 5}};
	
	static int[][] toonValuePerRide = {{3, 0, 5, 6, 6, 5}, {8, 2, 5, 3, 5, 10}, 
			{2, 0, 5, 10, 2, 7}, {4, 2, 1, 2, 5, 5}, {2, 0, 5, 10, 0, 4}, {6, 6, 3, 2, 6, 6}, 
			{3, 5, 1, 0, 4, 3}, {8, 5, 5, 8, 12, 10}, {4, 0, 8, 12, 8, 10}, {5, 3, 3, 3, 6, 8}, 
			{4, 0, 18, 30, 7, 10}, {3, 0, 7, 6, 4, 2}, {2, 0, 8, 10, 12, 4}, {15, 10, 15, 8, 20, 25}, 
			{4, 0, 10, 12, 8, 5}, {8, 3, 5, 7, 10, 8}, {7, 15, 8, 5, 8, 10}, {2, 0, 5, 10, 18, 15}, 
			{7, 0, 23, 30, 0, 18}, {25, 18, 23, 20, 30, 27}, {8, 6, 30, 25, 35, 45}, {5, 0, 20, 25, 20, 23}};
	
	static int[][][] primeTimeBonuses = {
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{1, 0, 3, 6, 0, 1}, {3, 4, 1, 1, 3, 5}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{2, 0, 5, 10, 4, 5}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {1, 0, 4, 5, 3, 2}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {1, 0, 1, 3, 4, 3}, {1, 0, 5, 6, 0, 4}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}, 
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{2, 0, 5, 10, 4, 5}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {1, 0, 1, 3, 4, 3}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{2, 0, 5, 10, 4, 5}, {3, 2, 3, 3, 4, 5}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {1, 0, 1, 3, 4, 3}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {1, 0, 3, 5, 1, 4}, {1, 1, 1, 1, 3, 4}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {2, 2, 3, 2, 4, 5}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {5, 3, 5, 4, 5, 4}, 
			{0, 0, 0, 0, 0, 0}, {3, 2, 3, 3, 4, 5}, {0, 0, 0, 0, 0, 0}, {2, 0, 5, 4, 2, 1}, 
			{1, 0, 2, 3, 5, 2}, {0, 0, 0, 0, 0, 0}, {1, 0, 4, 5, 3, 2}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{2, 2, 5, 4, 5, 6}, {0, 0, 0, 0, 0, 0}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {5, 3, 5, 4, 5, 4}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {2, 0, 5, 4, 2, 1}, 
			{1, 0, 2, 3, 5, 2}, {0, 0, 0, 0, 0, 0}, {1, 0, 4, 5, 3, 2}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{2, 2, 5, 4, 5, 6}, {0, 0, 0, 0, 0, 0}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {5, 3, 5, 4, 5, 4}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {2, 0, 5, 4, 2, 1}, 
			{1, 0, 2, 3, 5, 2}, {0, 0, 0, 0, 0, 0}, {1, 0, 4, 5, 3, 2}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{2, 2, 5, 4, 5, 6}, {0, 0, 0, 0, 0, 0}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {5, 3, 5, 4, 5, 4}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {2, 0, 5, 4, 2, 1}, 
			{1, 0, 2, 3, 5, 2}, {0, 0, 0, 0, 0, 0}, {1, 0, 4, 5, 3, 2}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{2, 2, 5, 4, 5, 6}, {0, 0, 0, 0, 0, 0}},
				
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {5, 3, 5, 4, 5, 4}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {2, 0, 5, 4, 2, 1}, 
			{1, 0, 2, 3, 5, 2}, {0, 0, 0, 0, 0, 0}, {1, 0, 4, 5, 3, 2}, {2, 1, 1, 2, 3, 2}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{2, 2, 5, 4, 5, 6}, {1, 0, 4, 5, 3, 5}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{1, 0, 3, 6, 0, 1}, {0, 0, 0, 0, 0, 0}, {1, 3, 0, 0, 2, 2}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{1, 0, 3, 6, 0, 1}, {0, 0, 0, 0, 0, 0}, {1, 3, 0, 0, 2, 2}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{1, 0, 3, 6, 0, 1}, {0, 0, 0, 0, 0, 0}, {1, 3, 0, 0, 2, 2}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
			
			{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}
			
			
			};
	//Each sub-array #1 is an hour, each sub-array #2 is a ride
	
	static int[] rentValues = {60, 60, 100, 100, 120, 140, 140, 160, 180, 180, 200, 220, 220, 240, 260, 260, 280, 300, 300, 320, 350, 400};
	static int[] playerRent = new int[10];
	static int minRent = 60;
	
	static int[][] toonsBought = new int[10][22];
	//toons per ride per player
	
	static boolean[][][] bought = new boolean[10][22][3];
	//ride, worker, intern for all rides per player
	
	static boolean[] quickBought = new boolean[22];
	
	static int daysFinished = 0;
	static int hourNumber = 0;
	
	static boolean[] internHogged = new boolean[22];
	
	static int prevWeather = 4;
	
	public static void main(String[] args) throws InterruptedException {
		
		Arrays.fill(players, "");
		Arrays.fill(workers, "");
		Arrays.fill(interns, "");
		
		System.out.println("* WELCOME TO TSUM TSUM KINGDOM *");
		System.out.println("================================");
		
		System.out.println("How many players?");
		numPlayers = Integer.parseInt(in.nextLine());
		
		for(int i = 0; i<numPlayers; i++) {
			System.out.println("Player "+(i+1)+":");
			players[i] = in.nextLine();
		}
		
		System.out.println("How many workers?");
		numWorkers = Integer.parseInt(in.nextLine());
		
		for(int i = 0; i<numWorkers; i++) {
			System.out.println("Worker "+(i+1)+":");
			workers[i] = in.nextLine();
		}
		
		System.out.println("How many interns?");
		numInterns = Integer.parseInt(in.nextLine());
		
		for(int i = 0; i<numInterns; i++) {
			System.out.println("Intern "+(i+1)+":");
			interns[i] = in.nextLine();
		}
		
		System.out.println("=============================================");
		
		System.out.print("Welcome to *Tsum Tsum Kingdom*, ");
		for(int i = 0; i<numPlayers-1; i++) {
			System.out.print(players[i]+", ");
		}
		System.out.println("and "+players[numPlayers-1]+"!");
		
		pregame();
		
	}
	
	public static void pregame() throws InterruptedException {
		
		//how much money each players has initially
		for(int i = 0; i<numPlayers; i++) {
			System.out.println("How many dollars do you have, "+players[i]+"?");
			playerCash[i] = Integer.parseInt(in.nextLine());
		}
		
		workersLeft = numWorkers;
		internsLeft = numInterns;
		
		dayEndBuying();
		
	}
	
	public static int maxCash() throws InterruptedException {
		//helper method that returns the maximum amount of cash that a single player has
		int max = 0;
		for(int i = 0; i<numPlayers; i++) {
			if(playerCash[i]>max) {
				max = playerCash[i];
			}
		}
		return max;
	}
	
	public static boolean fullHire(int wc) throws InterruptedException {
		//helper method that returns true if all rides have workers/interns
		for(int i = 0; i<numPlayers; i++) {
			for(int j = 0; j<22; j++) {
				if(bought[i][j][0]) {
					if(!bought[i][j][wc]) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public static void dayEndBuying() throws InterruptedException {
		
		System.out.println("===============================");
		
		boolean[] doneBuying = new boolean[numPlayers];
		boolean totalDoneBuying = false;
		
		//buying rides
		while(!totalDoneBuying) {
			totalDoneBuying = true;
			for(int i = 0; i<numPlayers; i++) {
				if(!doneBuying[i]) {
					if(playerCash[i]>=minRent) {
						System.out.println("Which ride would you like to buy, "+players[i]+"? (Type \"DONE\" if you are done buying)");
						String buy = in.nextLine();
						if(buy.equals("DONE")) {
							doneBuying[i] = true;
						} else {
							totalDoneBuying = false;
							boolean found = false;
							
							for(int j = 0; j<22; j++) {
								if(buy.indexOf(rides[j])>=0 && !quickBought[j] && playerCash[i]>=rentValues[j]) {
									found = true;
									quickBought[j] = true;
									bought[i][j][0] = true;
									playerCash[i] -= rentValues[j];
									playerRent[i] += (rentValues[j])/2;
									System.out.println("Please pay $"+rentValues[j]+" to the bank.");
								}
							}
							
							if(!found) {
								System.out.println("Something went wrong. This input was ignored.");
								i--;
							}
						}
					} else {
						doneBuying[i] = true;
					}
				}
			}
		}
		
		boolean workerDone = false;
		
		//buying workers
		while(!workerDone && workersLeft != 0 && maxCash()>=50 && !fullHire(1)) {
			System.out.println("Who would like to hire a worker? (Type \"NOBODY\" if nobody wants to hire a worker)");
			String person = in.nextLine();
			if(person.equals("NOBODY")) {
				workerDone = true;
			} else {
				int personNum = 0;
				
				boolean found = false;
				
				for(int i = 0; i<numPlayers; i++) {
					if(players[i].equals(person)) {
						personNum = i;
						found = true;
						break;
					}
				}
				
				if(!found) {
					System.out.println("We couldn't find that name. This input was ignored.");
					continue;
				}
				
				if(playerCash[personNum]>=50) {
					System.out.println(players[personNum]+", which ride would you like to hire a worker for?");
					String ride = in.nextLine();
					int rideNum = 0;
					
					found = false;
					
					for(int i = 0; i<22; i++) {
						if(ride.indexOf(rides[i])>=0) {
							rideNum = i;
							found = true;
							break;
						}
					}
					
					if(!found) {
						System.out.println("That doesn't seem like a ride. This input was ignored.");
						continue;
					}
					
					System.out.println(players[personNum]+", who would you like to hire?");
					String hired = in.nextLine();
					
					found = false;
					
					int workerNum = 0;
					
					for(int i = 0; i<numWorkers; i++) {
						if(hired.equals(workers[i])) {
							found = true;
							workerNum = i;
							workerCash[i] += 50;
							playerCash[personNum] -= 50;
							bought[personNum][rideNum][1] = true;
							workersLeft--;
							break;
						}
					}
					
					if(!found) {
						System.out.println("We couldn't find that name. This input was ignored.");
					} else {
						System.out.println(players[personNum]+", please pay "+workers[workerNum]+" $50.");
					}
				} else {
					System.out.println("Sorry, you don't have enough money to hire a worker.");
				}
				
			}
		}
		
		boolean toonDone = false;
		
		//buying toontown houses
		while(!toonDone) {
			System.out.println("Who would like to buy a toontown house? (Type \"NOBODY\" if nobody wants to buy a toontown house)");
			String person = in.nextLine();
			if(person.equals("NOBODY")) {
				toonDone = true;
			} else {
				int personNum = 0;
				
				boolean found = false;
				
				for(int i = 0; i<numPlayers; i++) {
					if(players[i].equals(person)) {
						personNum = i;
						found = true;
						break;
					}
				}
				
				if(!found) {
					System.out.println("We couldn't find that name. This input was ignored.");
					continue;
				}
				
				System.out.println(players[personNum]+", which ride would you like to buy a toontown house for?");
				String ride = in.nextLine();
				int rideNum = 0;
				
				found = false;
				
				for(int i = 0; i<22; i++) {
					if(ride.indexOf(rides[i])>=0) {
						rideNum = i;
						found = true;
						break;
					}
				}
				
				if(!found) {
					System.out.println("That doesn't seem like a ride. This input was ignored.");
					continue;
				}
				
				System.out.println(players[personNum]+", how many toontown houses would you like to buy?");
				int numToons = Integer.parseInt(in.nextLine());
				int cost = 0;
				
				if(rideNum<5) {
					cost = numToons*50;
				} else if(rideNum<11) {
					cost = numToons*100;
				} else if(rideNum<17) {
					cost = numToons*150;
				} else {
					cost = numToons*200;
				}
				
				if(playerCash[personNum]>=cost) {
					playerCash[personNum]-=cost;
					toonsBought[personNum][rideNum]+=numToons;
					System.out.println("Please pay $"+cost+" to the bank.");
				} else {
					System.out.println("Sorry, you don't have enough money to buy that.");
				}
			}
		}
		
		boolean internDone = false;
		
		//buying interns
		while(!internDone && internsLeft != 0 && maxCash()>=13 && !fullHire(2)) {
			System.out.println("Who would like to hire a intern for the whole day? (Type \"NOBODY\" if nobody wants to buy a intern for the whole day)");
			String person = in.nextLine();
			if(person.equals("NOBODY")) {
				internDone = true;
			} else {
				int personNum = 0;
				
				boolean found = false;
				
				for(int i = 0; i<numPlayers; i++) {
					if(players[i].equals(person)) {
						personNum = i;
						found = true;
						break;
					}
				}
				
				if(!found) {
					System.out.println("We couldn't find that name. This input was ignored.");
					continue;
				}
				
				if(playerCash[personNum]>=13) {
					System.out.println(players[personNum]+", which ride would you like to hire a full-day intern for?");
					String ride = in.nextLine();
					int rideNum = 0;
					
					found = false;
					
					for(int i = 0; i<22; i++) {
						if(ride.indexOf(rides[i])>=0) {
							rideNum = i;
							found = true;
							break;
						}
					}
					
					if(!found) {
						System.out.println("That doesn't seem like a ride. This input was ignored.");
						continue;
					}
					
					System.out.println(players[personNum]+", who would you like to hire?");
					String hired = in.nextLine();
					
					found = false;
					
					int internNum = 0;
					
					for(int i = 0; i<numInterns; i++) {
						if(hired.equals(interns[i])) {
							found = true;
							internNum = i;
							internCash[i] += 13;
							playerCash[personNum] -= 13;
							bought[personNum][rideNum][2] = true;
							internHogged[rideNum] = true;
							internsLeft--;
							break;
						}
					}
					
					if(!found) {
						System.out.println("We couldn't find that name. This input was ignored.");
					} else {
						System.out.println(players[personNum]+", please pay "+interns[internNum]+" $13.");
					}
				} else {
					System.out.println("Sorry, you don't have enough money to hire a intern for the whole day.");
				}
				
			}
		}
		
		play();
	}
	
	public static void play() throws InterruptedException {
		
		if(hourNumber == 0)
		System.out.println("Day "+(daysFinished+1)+" is about to begin!");
		
		System.out.println("===============");
		
		TimeUnit.SECONDS.sleep(1);
		
		int time = hourNumber+9;
		boolean pm = false;
		if(time>12) {
			time-=12;
			pm = true;
		} else if(time == 12) {
			pm = true;
		}
		
		if(hourNumber == 13) {
			System.out.println("Good night! The day is over!");
			dayEndReset();
		} else {
			System.out.print("It is "+time+":00 ");
			
			if(pm) {
				System.out.println("pm!");
			} else {
				System.out.println("am!");
			}
			
			hour();
		}
		
	}
	
	public static void hour() throws InterruptedException {
		
		boolean internDone = false;
		
		//hiring interns
		while(!internDone && internsLeft != 0 && !fullHire(2)) {
			System.out.println("Who would like to hire a intern for the rest of the day? (Type \"NOBODY\" if nobody wants to hire a intern for the rest of the day)");
			String person = in.nextLine();
			if(person.equals("NOBODY")) {
				internDone = true;
			} else {
				int personNum = 0;
				
				boolean found = false;
				
				for(int i = 0; i<numPlayers; i++) {
					if(players[i].equals(person)) {
						personNum = i;
						found = true;
						break;
					}
				}
				
				if(!found) {
					System.out.println("We couldn't find that name. This input was ignored.");
					continue;
				}
				
				if(playerCash[personNum]>=1) {
					System.out.println(players[personNum]+", which ride would you like to hire a intern for?");
					String ride = in.nextLine();
					int rideNum = 0;
					
					found = false;
					
					for(int i = 0; i<22; i++) {
						if(ride.indexOf(rides[i])>=0) {
							rideNum = i;
							found = true;
							break;
						}
					}
					
					if(!found) {
						System.out.println("That doesn't seem like a ride. This input was ignored.");
						continue;
					}
					
					System.out.println(players[personNum]+", who would you like to hire?");
					String hired = in.nextLine();
					
					found = false;
					
					for(int i = 0; i<numInterns; i++) {
						if(hired.equals(interns[i])) {
							found = true;
							internCash[i] += 13-hourNumber;
							playerCash[personNum] -= 13-hourNumber;
							System.out.println(players[personNum]+", please pay "+interns[i]+" $"+(13-hourNumber)+".");
							bought[personNum][rideNum][2] = true;
							internHogged[rideNum] = true;
							internsLeft--;
							break;
						}
					}
					
					if(!found) {
						System.out.println("We couldn't find that name. This input was ignored.");
					}
				} else {
					System.out.println("Sorry, you don't have enough money to hire a intern.");
				}
			}
		}
		
		if(internsLeft != 0) {
			internDone = false;
		}
		
		//hiring interns
		int internsBought = 0;
		while(!internDone && internsLeft-internsBought != 0 && !fullHire(2)) {
			System.out.println("Who would like to hire a intern for an hour? (Type \"NOBODY\" if nobody wants to hire a intern for an hour)");
			String person = in.nextLine();
			if(person.equals("NOBODY")) {
				internDone = true;
			} else {
				int personNum = 0;
				
				boolean found = false;
				
				for(int i = 0; i<numPlayers; i++) {
					if(players[i].equals(person)) {
						personNum = i;
						found = true;
						break;
					}
				}
				
				if(!found) {
					System.out.println("We couldn't find that name. This input was ignored.");
					continue;
				}
				
				if(playerCash[personNum]>=1) {
					System.out.println(players[personNum]+", which ride would you like to hire a intern for?");
					String ride = in.nextLine();
					int rideNum = 0;
					
					found = false;
					
					for(int i = 0; i<22; i++) {
						if(ride.indexOf(rides[i])>=0) {
							rideNum = i;
							found = true;
							break;
						}
					}
					
					if(!found) {
						System.out.println("That doesn't seem like a ride. This input was ignored.");
						continue;
					}
					
					System.out.println(players[personNum]+", who would you like to hire?");
					String hired = in.nextLine();
					
					found = false;
					
					int internNum = 0;
					
					for(int i = 0; i<numInterns; i++) {
						if(hired.equals(interns[i])) {
							found = true;
							internNum = i;
							internCash[i] ++;
							playerCash[personNum] --;
							bought[personNum][rideNum][2] = true;
							internsBought++;
							break;
						}
					}
					
					if(!found) {
						System.out.println("We couldn't find that name. This input was ignored.");
					} else {
						System.out.println(players[personNum]+", please pay "+interns[internNum]+" $1.");
					}
				} else {
					System.out.println("Sorry, you don't have enough money to buy a intern.");
				}
				
			}
		}
		
		
		int weather = prevWeather;
		
		double probChange = Math.random();
		
		if(probChange<0.4) {
			if(weather == 1) {
				weather = 6;
			} else if(weather == 3) {
				weather = 4;
			} else {
				weather--;
			}
		} else if(probChange>0.6) {
			if(weather == 6) {
				weather = 1;
			} else if(weather == 2) {
				weather = 1;
			} else {
				weather++;
			}
		} else if(probChange<0.5 && weather == 4) {
			weather--;
		} else if(probChange>0.5 && weather == 1) {
			weather++;
		}
		
		prevWeather = weather;
		
		int dex = (int)(Math.random()*4);
		String[][] messages = {{"It's going to be wet and messy!", "Umbrellas optional!", "No need to water plants!", "Perfect day to go swimming!"},
					{"All outdoor rides are closed!", "Watch out for lightning!", "Everyone go inside!", "Can you hear the thunder?"},
					{"People are going inside!", "Ice cream is melting!", "Wear sunscreen!", "Drink lots of water!"},
					{"Everyone is outside!", "It's a beautiful day!", "Perfect day for ice cream!", "It's busy in the parks!"},
					{"Watch out for weather-related delays!", "Don't lose your hat!", "Watch for falling trees!", "Hold on to your park maps!"},
					{"No ice cream today!", "Wear a sweater!", "Don't go to the water park!", "Elsa has arrived!"}};
		
		if(weather == 1) {
			System.out.print("It is drizzling! ");
		} else if(weather == 2) {
			System.out.print("It is stormy! ");
		} else if(weather == 3) {
			System.out.print("It is scorching! ");
		} else if(weather == 4) {
			System.out.print("It is sunny! ");
		} else if(weather == 5) {
			System.out.print("It is windy! ");
		} else if(weather == 6) {
			System.out.print("It is cold! ");
		}
		
		System.out.println(messages[weather-1][dex]);
		
		TimeUnit.SECONDS.sleep(1);
		
		//earn money
		for(int i = 0; i<numPlayers; i++) {
			int cashEarned = 0;
			for(int j = 0; j<22; j++) {
				if(bought[i][j][0]) {
					cashEarned+=baseValuePerRide[j][weather-1];
					cashEarned+=primeTimeBonuses[hourNumber][j][weather-1];
					cashEarned+=toonsBought[i][j]*toonValuePerRide[j][weather-1];
					if(bought[i][j][1]) {
						cashEarned+=workerValuePerRide[j][weather-1];
					}
					if(bought[i][j][2]) {
						cashEarned+=internValuePerRide[j][weather-1];
					}
				}
			}
			playerCash[i] += cashEarned;
			System.out.println(players[i]+" earned $"+cashEarned+"!");
		}
		System.out.println("Press enter to continue.");
		in.nextLine();
		
		
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<22; j++) {
				if(!internHogged[j]) {
					bought[i][j][2] = false;
				}
			}
		}
		
		hourNumber++;
		
		play();
	}
	
	public static void dayEndReset() throws InterruptedException {
		hourNumber = 0;
		daysFinished++;
		Arrays.fill(internHogged, false);
		
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<22; j++) {
				bought[i][j][1] = false;
			}
		}
		
		System.out.println("======================================");
		
		for(int i = 0; i<numPlayers; i++) {
			
			System.out.print(players[i]+", your rent for today is $"+playerRent[i]+".");
			if(playerCash[i]<playerRent[i]) {
				System.out.println(" You do not have enough money to pay your rent.");
				System.out.println("Would you like to sell any property or toontown houses back to the bank?");
				
				String response = in.nextLine();
				
				boolean doneEverything = false;
				
				if(response.equalsIgnoreCase("yes")) {
					boolean doneToons = false;
					boolean doneProperty = false;
					
					while(!doneToons && !doneEverything) {
						System.out.println(players[i]+", which ride would you like to sell toontown houses from? (Type \"DONE\" if you would not like to sell toontown houses.)");
						String ride = in.nextLine();
						
						if(ride.equals("DONE")) {
							doneToons = true;
							break;
						}
						
						int rideNum = 0;
						
						boolean found = false;
						
						for(int j = 0; j<22; j++) {
							if(ride.indexOf(rides[j])>=0) {
								rideNum = j;
								found = true;
								break;
							}
						}
						
						if(!found) {
							System.out.println("That doesn't seem like a ride. This input was ignored.");
							continue;
						}
						
						System.out.println(players[i]+", how many toontown houses would you like to sell?");
						int numToons = Integer.parseInt(in.nextLine());
						int cost = 0;
						
						if(rideNum<5) {
							cost = numToons*25;
						} else if(rideNum<11) {
							cost = numToons*50;
						} else if(rideNum<17) {
							cost = numToons*75;
						} else {
							cost = numToons*100;
						}
						
						if(toonsBought[i][rideNum]-numToons>=0) {
							System.out.print("You have earned $"+cost+".");
							toonsBought[i][rideNum] -= numToons;
							playerCash[i] += cost;
							System.out.println(" Now you have $"+playerCash[i]+".");
							if(playerCash[i]>=playerRent[i]) {
								System.out.print("You have enough money to pay your rent. Please pay the bank $"+playerRent[i]+". ");
								doneEverything = true;
								playerCash[i] -= playerRent[i];
								System.out.println("You have $"+playerCash[i]+" left.");
								break;
							}
						} else {
							System.out.println("You don't have that much to sell. This input was ignored.");
						}
					}
					
					while(!doneEverything && !doneProperty) {
						System.out.println(players[i]+", which ride would you like to sell? (Type \"DONE\" if you would not like to sell rides.)");
						String ride = in.nextLine();
						
						if(ride.equals("DONE")) {
							doneProperty = true;
							break;
						}
						
						int rideNum = 0;
						
						boolean found = false;
						
						for(int j = 0; j<22; j++) {
							if(ride.indexOf(rides[j])>=0) {
								rideNum = j;
								found = true;
								break;
							}
						}
						
						if(!found) {
							System.out.println("That doesn't seem like a ride. This input was ignored.");
							continue;
						}
						
						if(bought[i][rideNum][0]) {
							bought[i][rideNum][0] = false;
							quickBought[rideNum] = false;
							playerRent[i] -= rentValues[rideNum]/2;
							playerCash[i] += rentValues[rideNum];
							System.out.println("You have earned $"+rentValues[rideNum]+". Now you have $"+playerCash[i]+".");
							
							if(playerCash[i]>=playerRent[i]) {
								System.out.print("You have enough money to pay your rent. Please pay the bank $"+playerRent[i]+". ");
								doneEverything = true;
								playerCash[i] -= playerRent[i];
								System.out.println("You have $"+playerCash[i]+" left.");
								break;
							}
						} else {
							System.out.println("It doesn't seem like you own that. This input was ignored.");
						}
						
					}
					
				}
				
				if(!doneEverything) {
					System.out.println("You must pay the rest of your money to the bank, return your property to the bank, and become a intern.");
					//add to intern database
					for(int j = 0; j<25; j++) {
						if(interns[j].equals("")) {
							interns[j] = players[i];
							numInterns++;
							break;
						}
					}
					
					//remove from player database
					numPlayers--;
					
					for(int j = i; j<9; j++) {
						players[j] = players[j+1];
						playerCash[j] = playerCash[j+1];
						playerRent[j] = playerRent[j+1];
						
						for(int k = 0; k<22; k++) {
							toonsBought[j][k] = toonsBought[j+1][k];
							
							if(bought[j][k][0]) {
								quickBought[k] = false;
							}
							
							for(int l = 0; l<3; l++) {
								bought[j][k][l] = bought[j+1][k][l];
							}
						}
					}
					
					i--;
				}
				
			} else {
				//just pay the rent
				playerCash[i] -= playerRent[i];
				System.out.println(" You have $"+playerCash[i]+" left.");
			}
		}
		
		boolean printed = false;
		
		//workers upgrade
		for(int i = 0; i<numWorkers; i++) {
			if(workerCash[i]>=250) {
				if(!printed) {
					System.out.println("======================================");
					printed = true;
				}
				
				System.out.println(workers[i]+", do you want to become a manager?");
				String response = in.nextLine();
				if(response.equalsIgnoreCase("yes")) {
					
					//add to player database
					for(int j = 0; j<10; j++) {
						if(players[j].equals("")) {
							players[j] = workers[i];
							playerCash[j] = workerCash[i];
							numPlayers++;
							break;
						}
					}
					
					//remove from worker database
					numWorkers--;
					for(int j = i; j<14; j++) {
						workers[j] = workers[j+1];
						workerCash[j] = workerCash[j+1];
					}
					
					i--;
				}
			}
		}
		
		printed = false;
		
		//interns upgrade
		for(int i = 0; i<numInterns; i++) {
			if(internCash[i]>=100) {
				if(!printed) {
					System.out.println("======================================");
					printed = true;
				}
				
				System.out.println(interns[i]+", do you want to become a manager?");
				String response = in.nextLine();
				if(response.equalsIgnoreCase("yes")) {
					
					//add to worker database
					for(int j = 0; j<10; j++) {
						if(workers[j].equals("")) {
							workers[j] = interns[i];
							workerCash[j] = internCash[i];
							numWorkers++;
							break;
						}
					}
					
					//remove from intern database
					numInterns--;
					for(int j = i; j<14; j++) {
						interns[j] = interns[j+1];
						internCash[j] = internCash[j+1];
					}
					
					i--;
				}
			}
		}
		
		workersLeft = numWorkers;
		internsLeft = numInterns;
		
		dayEndBuying();
	}
	
}
