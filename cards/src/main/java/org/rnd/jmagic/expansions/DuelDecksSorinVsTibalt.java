package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Sorin vs. Tibalt")
public final class DuelDecksSorinVsTibalt extends SimpleExpansion
{
	public DuelDecksSorinVsTibalt()
	{
		super();

		this.addCards(Rarity.LAND, "Mountain", "Plains", "Swamp");
		this.addCards(Rarity.COMMON, "Absorb Vis", "Ashmouth Hound", "Blazing Salvo", "Blightning", "Bloodrage Vampire", "Bump in the Night", "Child of Night", "Coal Stoker", "Doomed Traveler", "Duskhunter Bat", "Evolving Wilds", "Faithless Looting", "Flame Slash", "Geistflame", "Goblin Arsonist", "Mad Prophet", "Mark of the Vampire", "Mesmeric Fiend", "Rakdos Carnarium", "Sorin's Thirst", "Strangling Soot", "Terminate", "Unmake", "Vampire Lacerator", "Vampire's Bite", "Vithian Stinger");
		this.addCards(Rarity.UNCOMMON, "Akoum Refuge", "Browbeat", "Corpse Connoisseur", "Decompose", "Fiend Hunter", "Flame Javelin", "Gang of Devils", "Gatekeeper of Malakir", "Hellspark Elemental", "Lingering Souls", "Mausoleum Guard", "Mortify", "Phantom General", "Pyroclasm", "Reassembling Skeleton", "Recoup", "Revenant Patriarch", "Scorched Rusalka", "Scourge Devil", "Sengir Vampire", "Shambling Remains", "Skirsdag Cultist", "Spectral Procession", "Tainted Field", "Torrent of Souls", "Urge to Feed", "Vampire Nighthawk", "Vampire Outcasts", "Wall of Omens", "Zealous Persecution");
		this.addCards(Rarity.RARE, "Ancient Craving", "Breaking Point", "Butcher of Malakir", "Death Grasp", "Devil's Play", "Field of Souls", "Hellrider", "Lavaborn Muse", "Sulfuric Vortex", "Twilight Drover");
		this.addCards(Rarity.MYTHIC, "Sorin, Lord of Innistrad", "Tibalt, the Fiend-Blooded");
	}
}
