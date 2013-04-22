<?php
	require("common.php");

	initializeDB();

	// Delete any games that failed to heartbeat and have "died"

	// PDO can't use bindValue for DB_TABLE or GAME_DEATH here, but we
	// control them so it's up to us to not crash the query or perform SQL
	// injection on ourselves
	$statement = $db->prepare("
		DELETE FROM " . DB_TABLE . "
		WHERE
			lastHeartbeat < NOW() - INTERVAL " . GAME_DEATH . "
	");
	$statement->execute();
	$statement->closeCursor();

	// Go get the list of games and print it out

	// PDO can't use bindValue for DB_TABLE here, but we control it so it's
	// up to us to not crash the query or perform SQL injection on
	// ourselves
	$statement = $db->prepare("
		SELECT
			hostPlayerName,
			IP,
			port,
			description,
			currentPlayers,
			maxPlayers,
			format
		FROM " . DB_TABLE . "
	");
	$statement->execute();

	while(FALSE !== ($row = $statement->fetch()))
	{
		$hostPlayerName = $row["hostPlayerName"];
		$IP = $row["IP"];
		$port = intval($row["port"]);
		$description = $row["description"];
		$currentPlayers = intval($row["currentPlayers"]);
		$maxPlayers = intval($row["maxPlayers"]);
		$format = $row["format"];

		echo(join(":", array($hostPlayerName, $IP, $port, $description, $currentPlayers, $maxPlayers, $format)) . "\n");
	}
	$statement->closeCursor();

	// No closing PHP tag so no extra output is sent
