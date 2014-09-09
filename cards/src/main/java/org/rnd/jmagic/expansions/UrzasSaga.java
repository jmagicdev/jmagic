package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Urza's Saga")
public final class UrzasSaga extends SimpleExpansion
{
	public UrzasSaga()
	{
		super(new String[] {"Absolute Grace", "Absolute Law", "Abundance", "Abyssal Horror", "Academy Researchers", "Acidic Soil", "Acridian", "Albino Troll", "Anaconda", "Angelic Chorus", "Angelic Page", "Annul", "Antagonism", "Arc Lightning", "Arcane Laboratory", "Argothian Elder", "Argothian Enchantress", "Argothian Swine", "Argothian Wurm", "Attunement", "Back to Basics", "Barrin's Codex", "Barrin, Master Wizard", "Bedlam", "Befoul", "Bereavement", "Blanchwood Armor", "Blanchwood Treefolk", "Blasted Landscape", "Blood Vassal", "Bog Raiders", "Brand", "Bravado", "Breach", "Brilliant Halo", "Bull Hippo", "Bulwark", "Cackling Fiend", "Carpet of Flowers", "Carrion Beetles", "Catalog", "Catastrophe", "Cathodion", "Cave Tiger", "Child of Gaea", "Chimeric Staff", "Citanul Centaurs", "Citanul Flute", "Citanul Hierophants", "Claws of Gix", "Clear", "Cloak of Mists", "Confiscate", "Congregate", "Contamination", "Copper Gnomes", "Coral Merfolk", "Corrupt", "Cradle Guard", "Crater Hellion", "Crazed Skirge", "Crosswinds", "Crystal Chimes", "Curfew", "Dark Hatchling", "Dark Ritual", "Darkest Hour", "Defensive Formation", "Despondency", "Destructive Urge", "Diabolic Servitude", "Disciple of Grace", "Disciple of Law", "Discordant Dirge", "Disenchant", "Disorder", "Disruptive Student", "Douse", "Dragon Blood", "Drifting Djinn", "Drifting Meadow", "Dromosaur", "Duress", "Eastern Paladin", "Electryte", "Elite Archers", "Elvish Herder", "Elvish Lyrist", "Enchantment Alteration", "Endless Wurm", "Endoskeleton", "Energy Field", "Exhaustion", "Exhume", "Exploration", "Expunge", "Faith Healer", "Falter", "Fault Line", "Fecundity", "Fertile Ground", "Fiery Mantle", "Fire Ants", "Flesh Reaver", "Fluctuator", "Fog Bank", "Forest", "Forest", "Forest", "Forest", "Fortitude", "Gaea's Bounty", "Gaea's Cradle", "Gaea's Embrace", "Gamble", "Gilded Drake", "Glorious Anthem", "Goblin Cadets", "Goblin Lackey", "Goblin Matron", "Goblin Offensive", "Goblin Patrol", "Goblin Raider", "Goblin Spelunkers", "Goblin War Buggy", "Gorilla Warrior", "Grafted Skullcap", "Great Whale", "Greater Good", "Greener Pastures", "Guma", "Hawkeater Moth", "Headlong Rush", "Healing Salve", "Heat Ray", "Herald of Serra", "Hermetic Study", "Hibernation", "Hidden Ancients", "Hidden Guerrillas", "Hidden Herd", "Hidden Predators", "Hidden Spider", "Hidden Stag", "Hollow Dogs", "Hopping Automaton", "Horseshoe Crab", "Humble", "Hush", "Ill-Gotten Gains", "Imaginary Pet", "Intrepid Hero", "Island", "Island", "Island", "Island", "Jagged Lightning", "Karn, Silver Golem", "Launch", "Lay Waste", "Lifeline", "Lightning Dragon", "Lilting Refrain", "Lingering Mirage", "Looming Shade", "Lotus Blossom", "Lull", "Lurking Evil", "Mana Leech", "Meltdown", "Metrognome", "Midsummer Revel", "Mishra's Helix", "Mobile Fort", "Monk Idealist", "Monk Realist", "Morphling", "Mountain", "Mountain", "Mountain", "Mountain", "No Rest for the Wicked", "Noetic Scales", "Okk", "Opal Acrolith", "Opal Archangel", "Opal Caryatid", "Opal Gargoyle", "Opal Titan", "Oppression", "Order of Yawgmoth", "Outmaneuver", "Pacifism", "Parasitic Bond", "Pariah", "Path of Peace", "Pegasus Charger", "Pendrell Drake", "Pendrell Flux", "Peregrine Drake", "Persecute", "Pestilence", "Phyrexian Colossus", "Phyrexian Ghoul", "Phyrexian Processor", "Phyrexian Tower", "Pit Trap", "Plains", "Plains", "Plains", "Plains", "Planar Birth", "Planar Void", "Polluted Mire", "Pouncing Jaguar", "Power Sink", "Power Taint", "Presence of the Master", "Priest of Gix", "Priest of Titania", "Purging Scythe", "Rain of Filth", "Rain of Salt", "Ravenous Skirge", "Raze", "Recantation", "Reclusive Wight", "Redeem", "Reflexes", "Rejuvenate", "Remembrance", "Remote Isle", "Reprocess", "Rescind", "Retaliation", "Retromancer", "Rewind", "Rumbling Crescendo", "Rune of Protection: Artifacts", "Rune of Protection: Black", "Rune of Protection: Blue", "Rune of Protection: Green", "Rune of Protection: Lands", "Rune of Protection: Red", "Rune of Protection: White", "Sanctum Custodian", "Sanctum Guardian", "Sandbar Merfolk", "Sandbar Serpent", "Sanguine Guard", "Scald", "Scoria Wurm", "Scrap", "Seasoned Marshal", "Serra Avatar", "Serra Zealot", "Serra's Embrace", "Serra's Hymn", "Serra's Liturgy", "Serra's Sanctum", "Shimmering Barrier", "Shiv's Embrace", "Shivan Gorge", "Shivan Hellkite", "Shivan Raptor", "Show and Tell", "Shower of Sparks", "Sicken", "Silent Attendant", "Skirge Familiar", "Skittering Skirge", "Sleeper Agent", "Slippery Karst", "Smokestack", "Smoldering Crater", "Sneak Attack", "Somnophore", "Songstitcher", "Soul Sculptor", "Spined Fluke", "Spire Owl", "Sporogenesis", "Spreading Algae", "Steam Blast", "Stern Proctor", "Stroke of Genius", "Sulfuric Vapors", "Sunder", "Swamp", "Swamp", "Swamp", "Swamp", "Symbiosis", "Tainted AEther", "Telepathy", "Temporal Aperture", "Thran Quarry", "Thran Turbine", "Thundering Giant", "Time Spiral", "Titania's Boon", "Titania's Chosen", "Tolarian Academy", "Tolarian Winds", "Torch Song", "Treefolk Seedlings", "Treetop Rangers", "Turnabout", "Umbilicus", "Unnerve", "Unworthy Dead", "Urza's Armor", "Vampiric Embrace", "Vebulid", "Veil of Birds", "Veiled Apparition", "Veiled Crocodile", "Veiled Sentry", "Veiled Serpent", "Venomous Fangs", "Vernal Bloom", "Viashino Outrider", "Viashino Runner", "Viashino Sandswimmer", "Viashino Weaponsmith", "Victimize", "Vile Requiem", "Voice of Grace", "Voice of Law", "Voltaic Key", "Vug Lizard", "Wall of Junk", "War Dance", "Waylay", "Western Paladin", "Whetstone", "Whirlwind", "Wild Dogs", "Wildfire", "Windfall", "Winding Wurm", "Wirecat", "Witch Engine", "Wizard Mentor", "Worn Powerstone", "Worship", "Yawgmoth's Edict", "Yawgmoth's Will", "Zephid", "Zephid's Embrace"});
	}
}
