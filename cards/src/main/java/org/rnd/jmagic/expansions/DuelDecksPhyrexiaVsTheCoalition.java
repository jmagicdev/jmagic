package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Phyrexia vs. the Coalition")
public final class DuelDecksPhyrexiaVsTheCoalition extends SimpleExpansion
{
	public DuelDecksPhyrexiaVsTheCoalition()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Island", "Mountain", "Plains", "Swamp");
		this.addCards(Rarity.COMMON, "Armadillo Cloak", "Carrion Feeder", "Dark Ritual", "Exotic Curse", "Fertile Ground", "Gerrard's Command", "Harrow", "Hideous End", "Hornet", "Minion", "Narrow Escape", "Nomadic Elf", "Phyrexian Battleflies", "Phyrexian Broodlings", "Phyrexian Debaser", "Phyrexian Denouncer", "Phyrexian Ghoul", "Quirion Elves", "Saproling", "Tendrils of Corruption", "Terramorphic Expanse", "Thornscape Apprentice", "Tribal Flames", "Yavimaya Elder");
		this.addCards(Rarity.UNCOMMON, "Allied Strategies", "Bone Shredder", "Charging Troll", "Darigaaz's Charm", "Elfhame Palace", "Evasive Action", "Hornet Cannon", "Lightning Greaves", "Order of Yawgmoth", "Phyrexian Defiler", "Phyrexian Gargantua", "Phyrexian Hulk", "Phyrexian Totem", "Phyrexian Vault", "Power Armor", "Priest of Gix", "Puppet Strings", "Rith's Charm", "Sanguine Guard", "Shivan Oasis", "Slay", "Sunscape Battlemage", "Thornscape Battlemage", "Thunderscape Battlemage", "Treva's Charm", "Verduran Emissary", "Voltaic Key", "Whispersilk Cloak", "Worn Powerstone");
		this.addCards(Rarity.RARE, "Coalition Relic", "Darigaaz, the Igniter", "Gerrard Capashen", "Living Death", "Phyrexian Arena", "Phyrexian Colossus", "Phyrexian Plaguelord", "Phyrexian Processor", "Rith, the Awakener", "Treva, the Renewer");
		this.addCards(Rarity.MYTHIC, "Phyrexian Negator", "Urza's Rage");
	}
}
