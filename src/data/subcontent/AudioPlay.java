package data.subcontent;

import lui.base.data.LInitializable;

public class AudioPlay implements LInitializable {

	public String name = "";
	public int volume = 100;
	public int pitch = 100;
	public int time = 0;
	
	public AudioPlay() {}
	public AudioPlay(String path, int volume, int pitch, int time) {
		this.name = path;
		this.volume = volume;
		this.pitch = pitch;
		this.time = time;
	}

	@Override
	public void initialize() {
		name = "No sound selected";
	}

	@Override
	public String toString() {
		if (name.isEmpty())
			return "          ";
		return name.replace("sfx/", "") + 
				" " + volume + " " + pitch + " " + time;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof AudioPlay audio) {
            return audio.name.equals(name) && audio.pitch == pitch
					&& audio.volume == volume && audio.time == time;
		} else
			return false;
	}

}
