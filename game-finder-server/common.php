<?php
	require("config.php");

	/**
	 * Checks if each argument is a key in $_POST; if any aren't, an error
	 * is echoed and exit is called with a status of -1
	 * @param[in] ... String keys to check
	 */
	function checkPost()
	{
		foreach(func_get_args() as $key)
		{
			if(!array_key_exists($key, $_POST))
			{
				echo("Error: missing parameter \"" . $key . "\"");
				exit(-1);
			}
		}
	}

	/**
	 * Echo a message then call exit with a status of -2
	 * @param[in] $message The message to print
	 */
	function exitWithMessage($message)
	{
		echo($message);
		exit(-2);
	}

	/**
	 * Initialize the global variable $db as a PDO connection to the
	 * database as configured by the variables in "config.php"
	 */
	function initializeDB()
	{
		global $db;
		$db = new PDO(DB_DSN, DB_USER, DB_PASS, array(
			// Need to request a persistent connection when constructing the
			// handle so it actually has an effect
			PDO::ATTR_PERSISTENT => true
		));
		// Can't set this during construction since it's not "driver specific"
		$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_WARNING);
	}

	header("Content-Type: text/plain");

	// No closing PHP tag so no extra output is sent
