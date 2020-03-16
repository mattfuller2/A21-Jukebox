
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is a driver class for testing your <code>Song</code> and <code>PlayList</code> classes.
 *
 * This class will not compile until you have implemented both classes. You should write your
 * <code>Song</code> class first and test it using <code>SongTest.java</code>. Then, write your
 * <code>PlayList</code> class and test it using <code>PlayListTest.java</code>. And finally, make
 * sure everything works together and listen to your music using this class.
 *
 * @author CS121 Instructors
 */
public class Jukebox {

	/**
	 * Runs the program and handles all the menu options.
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("===========================");
		System.out.println("Welcome to the super jukebox!");
		System.out.println("===========================");

		PlayList playList = null;
		char option;
		do {
			System.out.println("You must create a playlist to get started.");
			System.out.println("---------------------------");
			System.out.println("(f) load playlist from file");
			System.out.println("(n) create new playlist");
			System.out.println("---------------------------");
			System.out.print("Choose an option: ");
			option = scan.next().trim().charAt(0);
			switch (option) {
				case 'f':
					playList = loadPlayList("playlist.txt");
					break;
				case 'n':
					System.out.print("Playlist name: ");
					String name = scan.nextLine().trim();
					playList = new PlayList(name);
					break;
				default:
					System.out.println("Invalid option.");
					break;
			}
		} while (playList == null);

		System.out.println("Playlist added.");
		System.out.println(playList);

		do {
			System.out.print("What do you want to do (type 'm' to show menu)? ");

			String line;
			do {
				line = scan.nextLine();
			} while (line.length() < 1);
			option = line.charAt(0);

			switch (option) {
				case 'm':
					System.out.println("(p) play song");
					System.out.println("(l) list songs");
					System.out.println("(a) add song");
					System.out.println("(d) delete song");
					System.out.println("(q) quit");
					System.out.println("(i) info");
					System.out.println();
					break;
				case 'p':
					if (playList.getNumSongs() > 0) {
						System.out.println(playList);
						int id;
						do {
							System.out.print("Choose a valid song id: ");
							id = Integer.parseInt(scan.next().trim());
						} while (id < 0 || id > playList.getNumSongs());
						System.out.println("Playing song: " + playList.playSong(id));
						System.out.println();
					} else {
						System.out.println("Playlist is empty.");
						System.out.println();
					}
					break;
				case 'a':
					Song song = readSong(scan);
					playList.addSong(song);
					System.out.println("Added song: " + song.getTitle());
					System.out.println(playList);
					System.out.println();
					break;
				case 'd':
					if (playList.getNumSongs() > 0) {
						System.out.println(playList);
						int id;
						do {
							System.out.print("Choose a valid song id: ");
							id = Integer.parseInt(scan.nextLine().trim());
						} while (id < 0 || id > playList.getNumSongs());
						playList.removeSong(id);
						System.out.println("Removed song.");
						System.out.println(playList);
						System.out.println();
					} else {
						System.out.println("Playlist is empty.");
					}
					break;
				case 'l':
					System.out.println(playList);
					System.out.println();
					break;
				case 'q':
					System.out.println("Goodbye!");
					break;
				case 'i':
					System.out.println(playList.getInfo());
					System.out.println();
					break;
				default:
					System.out.println("Invalid option.");
					break;
			}
		} while (option != 'q');
	}

	/**
	 * Reads song information from the user (via the scanner) and creates a new Song object.
	 *
	 * @param scan The input scanner to read from.
	 * @return The new song.
	 */
	public static Song readSong(Scanner scan) {
		System.out.println("Enter title: ");
		String title = scan.nextLine().trim();

		System.out.println("Enter artist: ");
		String artist = scan.nextLine().trim();

		System.out.println("Enter play time: ");

		// Catches invalid integers, such as ':' indicating conversion to seconds needed, and reads
		// the scanner using a String value and converts playTime into seconds.
		int playTime = 0;
		try {
			playTime = scan.nextInt();
		}
		catch (InputMismatchException e) {
			try {
				String value = scan.nextLine().trim();
				int colon = value.indexOf(':');
				int minutes = Integer.parseInt(value.substring(0, colon));
				int seconds = Integer.parseInt(value.substring(colon + 1));
				playTime = (minutes * 60) + seconds;
			}
			catch (NumberFormatException n) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("Enter file path: ");
		String filePath = scan.next().trim();

		Song song = new Song(title, artist, playTime, filePath);
		return song;
	}

	/**
	 * Loads a new playlist from a given file. The file should have the following format: File Name
	 * Song 1 Title Song 1 Artist Song 1 Play time Song 1 File path Song 2 Title Song 2 Artist Song
	 * 2 Play time Song 2 File path etc.
	 * 
	 * @param filename The name of the file to read the songs from.
	 * @return A new playlist containing songs with the attributes given in the file.
	 */
	public static PlayList loadPlayList(String filename) {
		PlayList list = null;
		try {
			Scanner scan = new Scanner(new File(filename));
			String playListName = scan.nextLine().trim();
			while (scan.hasNextLine()) {
				String title = scan.nextLine().trim();
				String artist = scan.nextLine().trim();
				String playtime = scan.nextLine().trim();
				String songPath = scan.nextLine().trim();

				int colon = playtime.indexOf(':');
				int minutes = Integer.parseInt(playtime.substring(0, colon));
				int seconds = Integer.parseInt(playtime.substring(colon + 1));
				int playtimesecs = (minutes * 60) + seconds;

				list = new PlayList(playListName);
				Song song = new Song(title, artist, playtimesecs, songPath);
				list.addSong(song);
			}
			scan.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("Failed to load playlist. " + e.getMessage());
		}
		return list;
	}
}
