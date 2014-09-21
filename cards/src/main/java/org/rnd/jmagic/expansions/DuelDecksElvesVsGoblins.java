package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Elves vs. Goblins")
public final class DuelDecksElvesVsGoblins extends SimpleExpansion
{
	public DuelDecksElvesVsGoblins()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Mountain");
		this.addCards(Rarity.COMMON, "Elemental", "Elf Warrior", "Elvish Eulogist", "Elvish Warrior", "Emberwilde Augur", "Forgotten Cave", "Giant Growth", "Goblin", "Goblin Cohort", "Goblin Sledder", "Llanowar Elves", "Lys Alana Huntmaster", "Mogg War Marshal", "Moonglove Extract", "Mudbutton Torchrunner", "Raging Goblin", "Skirk Prospector", "Skirk Shaman", "Spitting Earth", "Stonewood Invoker", "Tarfire", "Timberwatch Elf", "Tranquil Thicket", "Wellwisher", "Wildsize", "Wirewood Herald", "Wood Elves");
		this.addCards(Rarity.UNCOMMON, "Akki Coalflinger", "Boggart Shenanigans", "Elvish Harbinger", "Elvish Promenade", "Flamewave Invoker", "Gempalm Incinerator", "Gempalm Strider", "Goblin Burrows", "Goblin Matron", "Goblin Ringleader", "Goblin Warchief", "Harmonize", "Heedless One", "Imperious Perfect", "Mogg Fanatic", "Reckless One", "Skirk Drill Sergeant", "Sylvan Messenger", "Tar Pitcher", "Wirewood Lodge", "Wirewood Symbiote", "Wren's Run Vanquisher");
		this.addCards(Rarity.RARE, "Allosaurus Rider", "Ambush Commander", "Clickslither", "Ib Halfheart, Goblin Tactician", "Siege-Gang Commander", "Skirk Fire Marshal", "Slate of Ancestry", "Voice of the Woods");
	}
}
