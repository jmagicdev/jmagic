<?php
	require("common.php");

	checkPost("token");
	initializeDB();
	$remoteIP = $_SERVER["REMOTE_ADDR"];

	// PDO can't use bindValue for DB_TABLE, but we control it so it's up to
	// us to not crash the query or perform SQL injection on ourselves
	$statement = $db->prepare("
		DELETE FROM " . DB_TABLE . "
		WHERE
			IP = :IP AND
			token = :token
	");
	$statement->bindValue("IP", $remoteIP);
	$statement->bindValue("token", $_POST["token"]);
	$statement->execute();
	$statement->closeCursor();

	// No closing PHP tag so no extra output is sent
