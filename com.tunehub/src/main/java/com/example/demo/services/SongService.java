package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Song;

public interface SongService {

	public void addSong(Song song);

	public List<Song> fetctAllSongs();

	public boolean songExsits(String name);

	public void updateSong(Song song);
	
}