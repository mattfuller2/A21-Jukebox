
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class represent a playlist of songs using array lists.
 * 
 * @author Matt Fuller
 */
public class PlayList {
	private String name, songInfo;
	private Song playing;
	private ArrayList<Song> songList = new ArrayList<Song>();
	private int totalPlayTime = 0;

	/**
	 * Constructor: Accepts and sets a string value for the name of the PlayList. Initializes the
	 * current song playing and the songList to an empty list.
	 * 
	 * @param name of PlayList
	 */
	public PlayList(String name)
		{
			this.name = name;
			playing = null;
			songList = new ArrayList<Song>();
		}

	/**
	 * The parameter sets the name of the song.
	 * 
	 * @param name of song
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the song.
	 * 
	 * @return name of song
	 */
	public String getName() {
		return name;
	}

	/**
	 * Plays the song.
	 * 
	 * @param p Song playing
	 */
	public void setPlaying(Song p) {
		playing = p;
	}

	/**
	 * Returns the the song playing.
	 * 
	 * @return playing
	 */
	public Song getPlaying() {
		return playing;
	}

	/**
	 * Returns the ArrayList of Songs.
	 * 
	 * @return songList
	 */
	public ArrayList<Song> getSongList() {
		return songList;
	}

	/**
	 * Creates a Song object and adds it to the list.
	 * 
	 * @param songName name of the Song object created
	 */
	public void addSong(Song songName) {
		songList.add(songName);
	}

	/**
	 * Returns null if parameter index is out of range and returns songList.remove(i) if index is in
	 * bounds. Removes song from list.
	 * 
	 * @param index removes a Song object from list at index
	 * @return songList.remove(i) removes the Song from the list
	 */
	public Song removeSong(int index) {
		int i = index;
		for (i = index; index >= 0 && index < songList.size();) {
			return songList.remove(i);
		}
		if (i < 0 || i >= songList.size()) {
			return null;
		}
		songList.remove(i);
		return songList.get(i);
	}

	/**
	 * Returns the size of the ArrayList.
	 * 
	 * @return songList.size() size of list
	 */
	public int getNumSongs() {
		return songList.size();
	}

	/**
	 * Converts minutes and seconds into total seconds then returns the total play time of all songs
	 * in the playList.
	 * 
	 * @return totalPlayTime total play time in seconds of PlayList.
	 */
	public int getTotalPlayTime() {
		if (songList.size() == 0) {
			return 0;
		} else {
			for (Song s : songList) {
				totalPlayTime += s.getPlayTime();
			}
		}
		return totalPlayTime;
	}

	/**
	 * Returns a Song object based on the given parameter and returns null if parameter is out of
	 * range.
	 * 
	 * @param index gets Song object at index
	 * @return songList.get(s) a song in the list
	 */
	public Song getSong(int index) {
		int s = index;
		if (s >= 0 && s < songList.size()) {
			return songList.get(s);
		}
		return null;
	}

	/**
	 * Returns the song in the arraylist using the given parameter and returns no if the parameter
	 * is out of range.
	 * 
	 * @param id plays Song object at index
	 * @return playing play's song
	 */
	public Song playSong(int id) {
		int s = id;
		if (s < 0 || s >= songList.size()) {
			return null;
		}
		playing = getSong(s);
		setPlaying(playing);
		playing.play();
		return playing;
	}

	/**
	 * Returns a String that informs the user of the playlist's contents.
	 * 
	 * @return getInfo Playlist's details
	 */
	public String getInfo() {
		Song longestSong, shortestSong;
		DecimalFormat df = new DecimalFormat("0.00");

		songList = getSongList();

		if (getSongList().size() != 0) {
			longestSong = songList.get(0); // Longest song in playlist.
			shortestSong = songList.get(0); // Shortest song in playlis.

			double average = 0; // The playlist's average playtime in seconds.
			int sum = 0; // The playlist's total playtime in seconds.
			int listSize = getSongList().size(); // Number of songs in the playlist.

			// Updates the average, sum, and longest and shortest songs of the playlist.
			for (int i = 0; i < listSize; i++) {
				sum += songList.get(i).getPlayTime();
				totalPlayTime = sum;
				average = (double) sum / listSize;

				if (longestSong.getPlayTime() < songList.get(i).getPlayTime()) {
					longestSong = songList.get(i);
				} else if (shortestSong.getPlayTime() > songList.get(i).getPlayTime()) {
					shortestSong = songList.get(i);
				}

			}
			songInfo = "\nThe average play time is: " + df.format(average) + " seconds\n"
			        + "The shortest song is: " + shortestSong + "The longest song is: " + longestSong
			        + "Total play time: " + totalPlayTime + " seconds";

		}
		if (getSongList().size() == 0) {
			songInfo = "There are no songs.\n";
		}
		return songInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (getSongList().size() == 0) {
			builder.append("------------------\n");
			builder.append(name + " (" + 0 + " songs)\n");
			builder.append("------------------\n");
			builder.append("There are no songs.\n");
			builder.append("------------------\n");
			songInfo = builder.toString();
		}
		if (getSongList().size() > 0) {
			builder.append("------------------\n");
			builder.append(name + " (" + songList.size() + " songs)\n");
			builder.append("------------------\n");
			for (int i = 0; i < songList.size(); i++) {
				builder.append("(" + i + ") ");
				builder.append(String.format("%-20s %-20s %-25s %10d\n", songList.get(i).getTitle(), songList
				        .get(i).getArtist(), songList.get(i).getFilePath(), songList.get(i).getPlayTime()));
			}
			builder.append("------------------\n");
			songInfo = builder.toString();
		}

		return songInfo;
	}
}
