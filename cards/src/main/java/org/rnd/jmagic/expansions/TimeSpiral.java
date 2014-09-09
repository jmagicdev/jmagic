package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Time Spiral")
public final class TimeSpiral extends SimpleExpansion
{
	public TimeSpiral()
	{
		super(new String[] {"AEther Web", "AEtherflame Wall", "Academy Ruins", "Amrou Scout", "Amrou Seekers", "Ancestral Vision", "Ancient Grudge", "Angel's Grace", "Ashcoat Bear", "Aspect of Mongoose", "Assassinate", "Assembly-Worker", "Barbed Shocker", "Basal Sliver", "Basalt Gargoyle", "Benalish Cavalry", "Bewilder", "Blazing Blade Askari", "Bogardan Hellkite", "Bogardan Rager", "Bonesplitter Sliver", "Brass Gnat", "Brine Elemental", "Calciform Pools", "Call to the Netherworld", "Cancel", "Candles of Leng", "Careful Consideration", "Castle Raptors", "Cavalry Master", "Celestial Crusader", "Chameleon Blur", "Children of Korlis", "Chromatic Star", "Chronatog Totem", "Chronosavant", "Clockspinning", "Clockwork Hydra", "Cloudchaser Kestrel", "Coal Stoker", "Conflagrate", "Coral Trickster", "Corpulent Corpse", "Crookclaw Transmuter", "Curse of the Cabal", "Cyclopean Giant", "D'Avenant Healer", "Dark Withering", "Deathspore Thallid", "Deep-Sea Kraken", "Dementia Sliver", "Demonic Collusion", "Detainment Spell", "Divine Congregation", "Draining Whelk", "Dralnu, Lich Lord", "Dread Return", "Dreadship Reef", "Dream Stalker", "Drifter il-Dal", "Drudge Reavers", "Durkwood Baloth", "Durkwood Tracker", "Duskrider Peregrine", "Empty the Warrens", "Endrek Sahr, Master Breeder", "Errant Doomsayers", "Errant Ephemeron", "Eternity Snare", "Evangelize", "Evil Eye of Urborg", "Faceless Devourer", "Fallen Ideal", "Fathom Seer", "Feebleness", "Firemaw Kavu", "Firewake Sliver", "Flagstones of Trokair", "Flamecore Elemental", "Fledgling Mawcor", "Flickering Spirit", "Flowstone Channeler", "Fool's Demise", "Forest", "Forest", "Forest", "Forest", "Foriysian Interceptor", "Foriysian Totem", "Fortify", "Fortune Thief", "Fungal Reaches", "Fungus Sliver", "Fury Sliver", "Gauntlet of Power", "Gaze of Justice", "Gemhide Sliver", "Gemstone Caverns", "Ghitu Firebreathing", "Ghostflame Sliver", "Glass Asp", "Goblin Skycutter", "Gorgon Recluse", "Grapeshot", "Greater Gargadon", "Greenseeker", "Griffin Guide", "Ground Rift", "Gustcloak Cavalier", "Harmonic Sliver", "Haunting Hymn", "Havenwood Wurm", "Herd Gnarr", "Hivestone", "Hypergenesis", "Ib Halfheart, Goblin Tactician", "Icatian Crier", "Ignite Memories", "Ironclaw Buzzardiers", "Island", "Island", "Island", "Island", "Ith, High Arcanist", "Ivory Giant", "Ixidron", "Jaya Ballard, Task Mage", "Jedit's Dragoons", "Jhoira's Timebug", "Kaervek the Merciless", "Keldon Halberdier", "Kher Keep", "Knight of the Holy Nimbus", "Krosan Grip", "Liege of the Pit", "Lightning Axe", "Lim-Dûl the Necromancer", "Living End", "Locket of Yesterdays", "Looter il-Kor", "Lotus Bloom", "Magus of the Candelabra", "Magus of the Disk", "Magus of the Jar", "Magus of the Mirror", "Magus of the Scroll", "Mana Skimmer", "Mangara of Corondor", "Might Sliver", "Might of Old Krosa", "Mindlash Sliver", "Mindstab", "Mishra, Artificer Prodigy", "Mogg War Marshal", "Molder", "Molten Slagheap", "Momentary Blink", "Moonlace", "Mountain", "Mountain", "Mountain", "Mountain", "Mwonvuli Acid-Moss", "Mystical Teachings", "Nantuko Shaman", "Nether Traitor", "Nightshade Assassin", "Norin the Wary", "Opal Guardian", "Opaline Sliver", "Ophidian Eye", "Orcish Cannonade", "Outrider en-Kor", "Paradise Plume", "Paradox Haze", "Pardic Dragon", "Pendelhaven Elder", "Pentarch Paladin", "Pentarch Ward", "Penumbra Spider", "Phantom Wurm", "Phthisis", "Phyrexian Totem", "Pit Keeper", "Plague Sliver", "Plains", "Plains", "Plains", "Plains", "Plated Pegasus", "Plunder", "Premature Burial", "Primal Forcemage", "Prismatic Lens", "Psionic Sliver", "Psychotic Episode", "Pull from Eternity", "Pulmonic Sliver", "Quilled Sliver", "Reiterate", "Restore Balance", "Return to Dust", "Rift Bolt", "Riftwing Cloudskate", "Saffi Eriksdotter", "Sage of Epityr", "Saltcrusted Steppe", "Sangrophage", "Sarpadian Empires, Vol. VII", "Savage Thallid", "Scarwood Treefolk", "Scion of the Ur-Dragon", "Screeching Sliver", "Scryb Ranger", "Search for Tomorrow", "Sedge Sliver", "Sengir Nosferatu", "Serra Avenger", "Shadow Sliver", "Sidewinder Sliver", "Skittering Monstrosity", "Skulking Knight", "Slipstream Serpent", "Smallpox", "Snapback", "Spectral Force", "Spell Burst", "Spike Tiller", "Spiketail Drakeling", "Spinneret Sliver", "Spirit Loop", "Sporesower Thallid", "Sprite Noble", "Sprout", "Squall Line", "Stonebrow, Krosan Hero", "Stonewood Invocation", "Stormcloud Djinn", "Strangling Soot", "Strength in Numbers", "Stronghold Overseer", "Stuffy Doll", "Subterranean Shambler", "Sudden Death", "Sudden Shock", "Sudden Spoiling", "Sulfurous Blast", "Swamp", "Swamp", "Swamp", "Swamp", "Swarmyard", "Tectonic Fiend", "Teferi, Mage of Zhalfir", "Telekinetic Sliver", "Temporal Eddy", "Temporal Isolation", "Tendrils of Corruption", "Terramorphic Expanse", "Thallid Germinator", "Thallid Shell-Dweller", "Thelon of Havenwood", "Thelonite Hermit", "Thick-Skinned Goblin", "Think Twice", "Thrill of the Hunt", "Thunder Totem", "Tivadar of Thorn", "Tolarian Sentinel", "Traitor's Clutch", "Trespasser il-Vec", "Trickbind", "Triskelavus", "Tromp the Domains", "Truth or Tale", "Two-Headed Sliver", "Undying Rage", "Unyaro Bees", "Urborg Syphon-Mage", "Urza's Factory", "Vampiric Sliver", "Venser's Sliver", "Verdant Embrace", "Vesuva", "Vesuvan Shapeshifter", "Viashino Bladescout", "Viscerid Deepwalker", "Viscid Lemures", "Voidmage Husher", "Volcanic Awakening", "Walk the Aeons", "Watcher Sliver", "Weathered Bodyguards", "Weatherseed Totem", "Wheel of Fate", "Wipe Away", "Word of Seizing", "Wormwood Dryad", "Wurmcalling", "Yavimaya Dryad", "Zealot il-Vec"});
	}
}
