<?php
	/**
	 * The Data Source Name used to connect to the database server
	 *
	 * @see http://www.php.net/manual/en/pdo.drivers.php
	 */
	define("DB_DSN", "mysql:host=localhost;dbname=jmagic_games");

	/**
	 * What password to use when connecting to the database server
	 */
	define("DB_PASS", "password");

	/**
	 * What table to store the data into (the game-finder only needs one)
	 */
	define("DB_TABLE", "games");

	/**
	 * What username to use when connecting to the database server
	 */
	define("DB_USER", "username");

	/**
	 * How long a game must fail to heartbeat before it's considered dead
	 * and is removed from the list of games
	 *
	 * This needs to be in a format usable directly by MySQL's date/time
	 * functions.
	 * @see http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_date-add
	 */
	define("GAME_DEATH", "1 MINUTE");

	/**
	 * How long a user must wait between creating games to prevent spamming
	 * the game-finder
	 *
	 * This needs to be in a format usable directly by MySQL's date/time
	 * functions.
	 * @see http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_date-add
	 */
	define("RATE_LIMIT", "1 MINUTE");

	// No closing PHP tag so no extra output is sent
