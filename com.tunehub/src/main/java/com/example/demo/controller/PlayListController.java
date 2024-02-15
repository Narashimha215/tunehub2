package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlayListService;
import com.example.demo.services.SongService;



@Controller
public class PlayListController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlayListService playListService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		
		List<Song> songList = songService.fetctAllSongs();
		model.addAttribute("songs",songList);
		return "createPlaylist";
	}
	@GetMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		
		playListService.addPlaylist(playlist);
		//updating song table
		List<Song> songList = playlist.getSongs();
		for(Song  s : songList) {
			s.getPlaylist().add(playlist);
			//update song object in db
			songService.updateSong(s);
		}
		return "adminHome";
	}
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		List<Playlist> allPlaylists = playListService.fetchAllPlaylists();
		model.addAttribute("allPlaylists",allPlaylists);
		
		return "displayPlaylists";
	}
	
}
