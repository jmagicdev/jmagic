Thanks for downloading jMagic!  We hope you enjoy it.

For bug reports and feature requests, please post at
http://www.slightlymagic.net/forum/viewforum.php?f=93.

To start jMagic, run the enclosed jMagic.jar file.  Most Windows users can
simply double-click the file.  This will open the jMagic launcher.

The first thing you'll want to set up is where you'll find games.  Choose
Settings from the jMagic menu.  In the box labeled "Game finder:", type this:

http://games.slightlymagic.net/jmagic/

(final slash required!) and click OK.

In the launcher, type a name and choose your deck (there are lots of decks from
recent events in the decks folder that came with jMagic), then either click
"Find games" on the Connect tab or "Host Publicly" on the Host tab.

If you click "Find games", you'll see the games currently being listed on
slightlymagic.  The button above the list will refresh it.

It's also possible to host a game without listing it on the game finding server,
if you have a specific friend in mind you'd like to play a game with.  Clicking
"Host" will do this; then your friend just needs to type your IP on the Connect
tab and click "Connect".  (You can get your IP from whatismyip.com if you don't
know it.)

Hosting a game (either manually or using a game-finder) requires UPnP support
from your router. If your router doesn't support UPnP (or the UPnP
implementation is broken, like on a version 8 Linksys WRT54G), use the
(extremely ad-laden, but helpful) guide on http://www.portforward.com which has
instructions for almost every router in use. Since jMagic isn't listed in the
list of applications, use the "Default Guide" with whatever port you specify in
the port field when jMagic starts. Since jMagic's UPnP implementation doesn't
appear to work on MacOS X, setting up port-forwarding manually is required to
host games for players outside of a local network.

If you have card art, jMagic can display it in the art boxes of the cards.  It
expects the art files' names to be to be a card name with a .jpg extension, for
example "Jace Beleren.jpg", and for all the files to be in one folder.  Click
the Browse button next to the "Card art location" box at the top of the Settings
window and choose the folder where those images are stored. All of the art must
be cropped to just the art (no text, type-line, text-box, etc...).

You can easily use your own decks in jMagic.  Decklists must be plain text
files.  Blank lines and lines starting with "#" are ignored; all other lines
denote the cards in the deck.  "SB:" denotes a sideboard card and a number
indicates how many of the card are in the deck.  See the decks in the decks
folder for examples.

Choosing Game->Settings from the game's menu bar brings up a settings window.
* "Automatic Passes" allows you to configure when jMagic will automatically
    pass for you. A checked box indicates that jMagic should pass priority for 
    you automatically if nothing happens during that step. An unchecked box
    indicates that you always want to manually pass priority during that step.
* "Play" configures various miscellaneous options:
  * "Rotate opponents' cards" rotates your opponents cards 180 degrees.
  * "Always Show Actions Menu" causes a popup menu to appear when you click on 
      a card you'd like to cast/activate/suspend/etc, rather than immediately 
      performing that action (this option helps prevents misclicks). (If there 
      are multiple actions associated with a card, the menu will always come up.)
  * "Render damage" causes damage to be displayed above the power and toughness
      on cards on the battlefield, in red.
  * "Default zone display" changes which two zones come up by default on the 
      left side of the screen for new games.
  * "Arrows" allows you to change the colors of arrows that represent the 
      various relationships between cards.
* "Yields" will display any spells/abilities you've yielded to. To yield to 
    something for the rest of the game, click it while it's on the stack. You 
    can stop yielding to a spell or ability by unchecking it here. 