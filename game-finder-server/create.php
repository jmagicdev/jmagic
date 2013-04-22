<?php
	require("common.php");

	checkPost("hostPlayerName", "port", "description", "maxPlayers", "format");
	initializeDB();

	$hostPlayerName = strval($_POST["hostPlayerName"]);
	if(0 === strlen($hostPlayerName))
		exitWithMessage("Error: must have a non-empty hostPlayerName");
	if(FALSE !== strpos($hostPlayerName, ":"))
		exitWithMessage("Error: cannot have a colon (\":\") in hostPlayerName");

	$port = intval($_POST["port"]);
	if($port <= 0)
		exitWithMessage("Error: port must be a positive integer");

	$description = strval($_POST["description"]);
	if(FALSE !== strpos($description, ":"))
		exitWithMessage("Error: cannot have a colon (\":\") in description");

	$maxPlayers = intval($_POST["maxPlayers"]);
	if($maxPlayers <= 1)
		exitWithMessage("Error: maxPlayers must be an integer greater than 1");

	$format = strval($_POST["format"]);
	if(FALSE !== strpos($format, ":"))
		exitWithMessage("Error: cannot have a colon (\":\") in format");

	$remoteIP = $_SERVER["REMOTE_ADDR"];

	// PDO can't use bindValue for DB_TABLE or RATE_LIMIT here, but we
	// control them so it's up to us to not crash the query or perform SQL
	// injection on ourselves
	$statement = $db->prepare("
		SELECT 1
		FROM " . DB_TABLE . "
		WHERE
			IP = :IP AND	
			NOW() - INTERVAL " . RATE_LIMIT . " < addTime
	");
	$statement->bindValue("IP", $remoteIP);
	$statement->execute();

	$row = $statement->fetch();
	$statement->closeCursor();
	if(FALSE !== $row)
		exitWithMessage("Error: making games too quickly; you must wait " . RATE_LIMIT . " between creation attempts");

	// There's no way to calculate the token while inserting the row
	// (because we won't know the ID until after the insert is successful)
	// so add the new row with a blank token now, calculate the token
	// afterwards, then update the row with the calculated token

	// PDO can't use bindValue for DB_TABLE here, but we control it so it's
	// up to us to not crash the query or perform SQL injection on ourselves
	$statement = $db->prepare("
		INSERT INTO " . DB_TABLE . "
		SET
			addTime = NOW(),
			hostPlayerName = :hostPlayerName,
			IP = :IP,
			port = :port,
			description = :description,
			currentPlayers = 1,
			maxPlayers = :maxPlayers,
			format = :format,
			lastHeartbeat = NOW()
	");
	$statement->bindValue("hostPlayerName", $hostPlayerName);
	$statement->bindValue("IP", $remoteIP);
	$statement->bindValue("port", $port);
	$statement->bindValue("description", $description);
	$statement->bindValue("maxPlayers", $maxPlayers);
	$statement->bindValue("format", $format);
	$statement->execute();
	$statement->closeCursor();

	// While a token based on the ID alone would be sufficent for
	// uniqueness, a smart observer might notice the MD5 hashes "counting
	// up", so use the current time to make the hash random. Even if an
	// attacker knows this algorithm, we don't expose what time a game is
	// created, so a brute-force search would be required.
	$ID = $db->lastInsertId();
	$token = md5($ID . time());

	// PDO can't use bindValue for DB_TABLE here, but we control it so it's
	// up to us to not crash the query or perform SQL injection on ourselves
	$statement = $db->prepare("
		UPDATE " . DB_TABLE . "
		SET token = :token
		WHERE ID = :ID
	");
	$statement->bindValue("token", $token);
	$statement->bindValue("ID", $ID);
	$statement->execute();
	$statement->closeCursor();

	echo($token);

	// No closing PHP tag so no extra output is sent
