Software requirements:
 - an SQL database server (MySQL recommended)
 - a web server capable of running PHP scripts (Apache httpd recommended)
 - the PHP Data Objects (PDO) library for PHP (the "php-pdo" package on most
   `yum`-based systems)
 - the necessary database driver for PHP (the "php-mysql" package on most
   `yum`-based systems when connecting to a MySQL database server)

Copy all the files into a directory accesible by web, then edit "config.php"
to provide the game-finder the values needed to connect to the database server.
Use "test.html" and read its source-code to see each function available to you
and what parameters each function requries. The first function you should run
from "test.html" is "Setup the game-finder for the first time" to create the
necessary table.

Make sure to test your game-finder before you advertise it to make sure you've
set it up correctly.
