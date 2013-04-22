<?php
	require("common.php");

	initializeDB();

	// PDO can't use bindValue for DB_TABLE, but we control it so it's up to
	// us to not crash the query or perform SQL injection on ourselves
	$statement = $db->prepare("
		CREATE TABLE IF NOT EXISTS " . DB_TABLE . "
		(
			ID INT NOT NULL auto_increment,
			addTime TIMESTAMP NOT NULL,
			hostPlayerName VARCHAR(64) NOT NULL,
			IP VARCHAR(64) NOT NULL,
			port INT NOT NULL,
			description VARCHAR(256) NOT NULL,
			currentPlayers INT NOT NULL,
			maxPlayers INT NOT NULL,
			format VARCHAR(256) NOT NULL,
			token VARCHAR(32) NOT NULL,
			lastHeartbeat TIMESTAMP NOT NULL,
			PRIMARY KEY (ID)
		 )
	");
	$statement->execute();
	$statement->closeCursor();

	echo("Table " . DB_TABLE . " created");

	// No closing PHP tag so no extra output is sent
