package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Magic: The Gathering-Commander")
public final class MagicTheGatheringCommander extends SimpleExpansion
{
	public MagicTheGatheringCommander()
	{
		super(new String[] {"Acidic Slime", "Acorn Catapult", "Æthersnipe", "Afterlife", "Akoum Refuge", "Akroma's Vengeance", "Akroma, Angel of Fury", "Alliance of Arms", "Angel of Despair", "Angelic Arbiter", "Anger", "Animar, Soul of Elements", "Aquastrand Spider", "Arbiter of Knollridge", "Archangel of Strife", "Armillary Sphere", "Artisan of Kozilek", "Attrition", "Aura Shards", "Austere Command", "Avatar of Fury", "Avatar of Slaughter", "Avatar of Woe", "Awakening Zone", "Azorius Chancery", "Azorius Guildmage", "Baloth Woodcrasher", "Barren Moor", "Basandra, Battle Seraph", "Bathe in Light", "Bestial Menace", "Bladewing the Risen", "Bojuka Bog", "Boros Garrison", "Boros Guildmage", "Boros Signet", "Brainstorm", "Brawn", "Breath of Darigaaz", "Brion Stoutarm", "Buried Alive", "Butcher of Malakir", "Call the Skybreaker", "Celestial Force", "Chain Reaction", "Champion's Helm", "Chaos Warp", "Chartooth Cougar", "Chorus of the Conclave", "Chromeshell Crab", "Cleansing Beam", "Cobra Trap", "Collective Voyage", "Colossal Might", "Comet Storm", "Command Tower", "Congregate", "Conundrum Sphinx", "Court Hussar", "Crescendo of War", "Cultivate", "Damia, Sage of Stone", "Dark Hatchling", "Darksteel Ingot", "Deadly Recluse", "Deadwood Treefolk", "Death by Dragons", "Death Mutation", "Desecrator Hag", "Diabolic Tutor", "Dimir Aqueduct", "Dimir Signet", "Disaster Radius", "Dominus of Fealty", "Doom Blade", "Dragon Whelp", "Dread Cacodemon", "Dreadship Reef", "Dreamborn Muse", "Dreamstone Hedron", "Duergar Hedge-Mage", "Earthquake", "Edric, Spymaster of Trest", "Electrolyze", "Elvish Aberration", "Eternal Witness", "Evincar's Justice", "Evolving Wilds", "Explosive Vegetation", "Extractor Demon", "Fact or Fiction", "Fallen Angel", "False Prophet", "Faultgrinder", "Fellwar Stone", "Fertilid", "Fierce Empath", "Fire // Ice", "Firespout", "Fists of Ironwood", "Flametongue Kavu", "Fleshbag Marauder", "Flusterstorm", "Fog Bank", "Footbottom Feast", "Forest", "Forest", "Forest", "Forest", "Forgotten Cave", "Fungal Reaches", "Furnace Whelp", "Garruk Wildspeaker", "Ghave, Guru of Spores", "Ghostly Prison", "Goblin Cadets", "Golgari Guildmage", "Golgari Rot Farm", "Golgari Signet", "Gomazoa", "Grave Pact", "Gravedigger", "Gruul Signet", "Gruul Turf", "Guard Gomazoa", "Gwyllion Hedge-Mage", "Harmonize", "Hex", "Homeward Path", "Hornet Queen", "Hour of Reckoning", "Howling Mine", "Hull Breach", "Hunting Pack", "Hydra Omnivore", "Insurrection", "Intet, the Dreamer", "Invigorate", "Island", "Island", "Island", "Island", "Izzet Boilerworks", "Izzet Chronarch", "Izzet Signet", "Jötun Grunt", "Journey to Nowhere", "Jwar Isle Refuge", "Kaalia of the Vast", "Karador, Ghost Chieftain", "Kazandu Refuge", "Kodama's Reach", "Krosan Tusker", "Lash Out", "Lhurgoyf", "Lightkeeper of Emeria", "Lightning Greaves", "Living Death", "Lonely Sandbar", "Magmatic Force", "Magus of the Vineyard", "Malfegor", "Mana-Charged Dragon", "Martyr's Bond", "Master Warcraft", "Memory Erosion", "Minds Aglow", "Molten Slagheap", "Monk Realist", "Mortify", "Mortivore", "Mother of Runes", "Mountain", "Mountain", "Mountain", "Mountain", "Mulldrifter", "Murmurs from Beyond", "Nantuko Husk", "Necrogenesis", "Nemesis Trap", "Nezumi Graverobber", "Nin, the Pain Artist", "Nucklavee", "Numot, the Devastator", "Oblation", "Oblivion Ring", "Oblivion Stone", "Oni of Wild Places", "Orim's Thunder", "Oros, the Avenger", "Orzhov Basilica", "Orzhov Guildmage", "Orzhov Signet", "Path to Exile", "Patron of the Nezumi", "Penumbra Spider", "Perilous Research", "Plains", "Plains", "Plains", "Plains", "Plumeveil", "Pollen Lullaby", "Prison Term", "Propaganda", "Prophetic Bolt", "Prophetic Prism", "Punishing Fire", "Pyrohemia", "Rakdos Carnarium", "Rakdos Signet", "Rapacious One", "Ray of Command", "Razorjaw Oni", "Reins of Power", "Reiver Demon", "Relic Crush", "Repulse", "Return to Dust", "Riddlekeeper", "Righteous Cause", "Riku of Two Reflections", "Rise from the Grave", "Ruhan of the Fomori", "Ruination", "Rupture Spire", "Sakura-Tribe Elder", "Savage Twister", "Scattering Stroke", "Scavenging Ooze", "Scythe Specter", "Secluded Steppe", "Selesnya Evangel", "Selesnya Guildmage", "Selesnya Sanctuary", "Selesnya Signet", "Serra Angel", "Sewer Nemesis", "Shared Trauma", "Shattered Angel", "Shriekmaw", "Sigil Captain", "Sign in Blood", "Simic Growth Chamber", "Simic Signet", "Simic Sky Swallower", "Skullbriar, the Walking Grave", "Skullclamp", "Skyscribing", "Slipstream Eel", "Sol Ring", "Solemn Simulacrum", "Soul Snare", "Spawnwrithe", "Spell Crumple", "Spike Feeder", "Spitebellows", "Spurnmage Advocate", "Squallmonger", "Stitch Together", "Storm Herd", "Stranglehold", "Sulfurous Blast", "Svogthos, the Restless Tomb", "Swamp", "Swamp", "Swamp", "Swamp", "Symbiotic Wurm", "Syphon Flesh", "Syphon Mind", "Szadek, Lord of Secrets", "Tariel, Reckoner of Souls", "Temple of the False God", "Teneb, the Harvester", "Terminate", "Terramorphic Expanse", "The Mimeoplasm", "Trade Secrets", "Tranquil Thicket", "Trench Gorger", "Tribute to the Wild", "Triskelavus", "Troll Ascetic", "Unnerve", "Valley Rannet", "Vampire Nighthawk", "Vedalken Plotter", "Vengeful Rebirth", "Veteran Explorer", "Vish Kal, Blood Arbiter", "Vision Skeins", "Vivid Crag", "Vivid Creek", "Vivid Grove", "Vivid Marsh", "Vivid Meadow", "Voice of All", "Vorosh, the Hunter", "Vow of Duty", "Vow of Flight", "Vow of Lightning", "Vow of Malice", "Vow of Wildness", "Vulturous Zombie", "Wall of Denial", "Wall of Omens", "Whirlpool Whelm", "Wild Ricochet", "Windborn Muse", "Windfall", "Wonder", "Wrecking Ball", "Wrexial, the Risen Deep", "Yavimaya Elder", "Zedruu the Greathearted", "Zoetic Cavern"});
	}
}
