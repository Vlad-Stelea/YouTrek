-- Initial DB Schema

CREATE TABLE IF NOT EXISTS tlps (
	id INT NOT NULL,
	base_url VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS videos (
	id INT NOT NULL,
	name VARCHAR(255) NOT NULL,
	is_remote BOOLEAN NOT NULL DEFAULT false,
	is_available BOOLEAN NOT NULL DEFAULT false,
	url VARCHAR(255),
	dialogue VARCHAR(255),
	date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	tlp_id INT,
	PRIMARY KEY (id),
	FOREIGN KEY (tlp_id)
		REFERENCES tlps (id)
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS characters (
	id INT,
	name VARCHAR(255) NOT NULL DEFAULT 'CHEKOV',
	PRIMARY KEY (id, name)
);

CREATE TABLE IF NOT EXISTS playlists (
	id INT NOT NULL,
	name VARCHAR(255) NOT NULL,
	date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pvjoin (
	playlist_id INT NOT NULL,
	video_id INT NOT NULL,
	video_order int,
	PRIMARY KEY (playlist_id, video_id),
	FOREIGN KEY (playlist_id) 
		REFERENCES playlists (id)
		ON DELETE CASCADE,
	FOREIGN KEY (video_id)
		REFERENCES videos (id)
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS vcjoin (
	video_id INT NOT NULL,
	character_id INT NOT NULL,
	PRIMARY KEY (video_id, character_id),
	FOREIGN KEY (video_id)
		REFERENCES videos (id)
		ON DELETE CASCADE,
	FOREIGN KEY (character_id)
		REFERENCES characters (id)
		ON DELETE CASCADE
);