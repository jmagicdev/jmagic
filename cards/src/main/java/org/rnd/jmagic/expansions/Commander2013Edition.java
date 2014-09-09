package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Commander 2013 Edition")
public final class Commander2013Edition extends SimpleExpansion
{
	public Commander2013Edition()
	{
		super(new String[] {"AEthermage's Touch", "Acidic Slime", "Act of Authority", "Aerie Mystics", "Ajani's Pridemate", "Akoum Refuge", "Angel of Finality", "Annihilate", "Arcane Denial", "Arcane Melee", "Arcane Sanctum", "Archangel", "Armillary Sphere", "Army of the Damned", "Augur of Bolas", "Augury Adept", "Avenger of Zendikar", "Azami, Lady of Scrolls", "Azorius Chancery", "Azorius Guildgate", "Azorius Herald", "Azorius Keyrune", "Baleful Force", "Baleful Strix", "Baloth Woodcrasher", "Bane of Progress", "Bant Panorama", "Barren Moor", "Basalt Monolith", "Behemoth Sledge", "Blood Rites", "Blue Sun's Zenith", "Bojuka Bog", "Boros Charm", "Boros Garrison", "Boros Guildgate", "Borrowing 100,000 Arrows", "Brilliant Plan", "Brooding Saurian", "Capricious Efreet", "Carnage Altar", "Charmbreaker Devils", "Charnelhoard Wurm", "Command Tower", "Conjurer's Closet", "Contested Cliffs", "Control Magic", "Cradle of Vitality", "Crater Hellion", "Crawlspace", "Crosis's Charm", "Cruel Ultimatum", "Crumbling Necropolis", "Cultivate", "Curse of Chaos", "Curse of Inertia", "Curse of Predation", "Curse of Shallow Graves", "Curse of the Forsaken", "Darksteel Ingot", "Darksteel Mutation", "Deadwood Treefolk", "Death Grasp", "Deathbringer Thoctar", "Deceiver Exarch", "Decree of Pain", "Deep Analysis", "Deepfire Elemental", "Derevi, Empyrial Tactician", "Dimir Guildgate", "Dirge of Dread", "Disciple of Griselbrand", "Dismiss", "Diviner Spirit", "Divinity of Pride", "Djinn of Infinite Deceits", "Drifting Meadow", "Dromar's Charm", "Druidic Satchel", "Drumhunter", "Dungeon Geists", "Echo Mage", "Elvish Skysweeper", "Endless Cockroaches", "Endrek Sahr, Master Breeder", "Esper Panorama", "Eternal Dragon", "Evolving Wilds", "Eye of Doom", "Faerie Conclave", "Famine", "Farhaven Elf", "Fecundity", "Fell Shepherd", "Fiend Hunter", "Fiery Justice", "Filigree Angel", "Fireball", "Fires of Yavimaya", "Fissure Vent", "Flickerform", "Flickerwisp", "Fog Bank", "Forest", "Forest", "Forest", "Forest", "Forgotten Cave", "Foster", "From the Ashes", "Furnace Celebration", "Gahiji, Honored One", "Goblin Bombardment", "Goblin Sharpshooter", "Golgari Guildgate", "Golgari Guildmage", "Golgari Rot Farm", "Grazing Gladehart", "Greed", "Grim Backwoods", "Grixis Charm", "Grixis Panorama", "Gruul Guildgate", "Guard Gomazoa", "Guttersnipe", "Hada Spy Patrol", "Harmonize", "Homeward Path", "Hooded Horror", "Hua Tuo, Honored Physician", "Hull Breach", "Hunted Troll", "Illusionist's Gambit", "Incendiary Command", "Inferno Titan", "Infest", "Island", "Island", "Island", "Island", "Izzet Boilerworks", "Izzet Guildgate", "Jace's Archivist", "Jade Mage", "Jar of Eyeballs", "Jeleva, Nephalia's Scourge", "Jund Charm", "Jund Panorama", "Jungle Shrine", "Jwar Isle Refuge", "Karmic Guide", "Kazandu Refuge", "Kazandu Tuskcaller", "Khalni Garden", "Kher Keep", "Kirtar's Wrath", "Kongming, \"Sleeping Dragon\"", "Krosan Grip", "Krosan Tusker", "Krosan Warchief", "Leafdrake Roost", "Leonin Bladetrap", "Lim-Dûl's Vault", "Llanowar Reborn", "Lonely Sandbar", "Lu Xun, Scholar General", "Magus of the Arena", "Marath, Will of the Wild", "Marrow Bats", "Mass Mutiny", "Mayael the Anima", "Mirari", "Mirror Entity", "Mistmeadow Witch", "Mnemonic Wall", "Mold Shambler", "Molten Disaster", "Molten Slagheap", "Mosswort Bridge", "Mountain", "Mountain", "Mountain", "Mountain", "Murkfiend Liege", "Myr Battlesphere", "Mystic Barrier", "Naya Charm", "Naya Panorama", "Naya Soulbeast", "Nekusar, the Mindrazer", "Nevinyrral's Disk", "New Benalia", "Night Soil", "Nightscape Familiar", "Nihil Spellbomb", "Nivix Guildmage", "Obelisk of Esper", "Obelisk of Grixis", "Obelisk of Jund", "Oloro, Ageless Ascetic", "One Dozen Eyes", "Opal Palace", "Ophiomancer", "Opportunity", "Order of Succession", "Orzhov Basilica", "Orzhov Guildgate", "Phantom Nantuko", "Phthisis", "Phyrexian Delver", "Phyrexian Gargantua", "Phyrexian Reclamation", "Pilgrim's Eye", "Plague Boiler", "Plains", "Plains", "Plains", "Plains", "Presence of Gond", "Price of Knowledge", "Primal Vigor", "Pristine Talisman", "Propaganda", "Prosperity", "Prossh, Skyraider of Kher", "Quagmire Druid", "Rain of Thorns", "Rakdos Carnarium", "Rakdos Guildgate", "Rakeclaw Gargantuan", "Rampaging Baloths", "Raven Familiar", "Ravenous Baloth", "Razor Hippogriff", "Reckless Spite", "Reincarnation", "Restore", "Roon of the Hidden Realm", "Rough // Tumble", "Rubinia Soulsinger", "Rupture Spire", "Sakura-Tribe Elder", "Saltcrusted Steppe", "Sanguine Bond", "Savage Lands", "Savage Twister", "Scarland Thrinax", "Seaside Citadel", "Secluded Steppe", "Seer's Sundial", "Sejiri Refuge", "Sek'Kuar, Deathkeeper", "Selesnya Charm", "Selesnya Guildgate", "Selesnya Guildmage", "Selesnya Sanctuary", "Selesnya Signet", "Serene Master", "Serra Avatar", "Sharding Sphinx", "Sharuum the Hegemon", "Shattergang Brothers", "Silklash Spider", "Simic Guildgate", "Simic Signet", "Skyscribing", "Skyward Eye Prophets", "Slice and Dice", "Slice in Twain", "Slippery Karst", "Smoldering Crater", "Sol Ring", "Soul Manipulation", "Spawning Grounds", "Spellbreaker Behemoth", "Sphinx of the Steel Wind", "Spinal Embrace", "Spine of Ish Sah", "Spitebellows", "Spiteful Visions", "Spoils of Victory", "Springjack Pasture", "Sprouting Thrinax", "Sprouting Vines", "Stalking Vengeance", "Starstorm", "Stonecloaker", "Stormscape Battlemage", "Strategic Planning", "Street Spasm", "Stronghold Assassin", "Sudden Demise", "Sudden Spoiling", "Sun Droplet", "Surveyor's Scope", "Survival Cache", "Swamp", "Swamp", "Swamp", "Swamp", "Swiftfoot Boots", "Sword of the Paruns", "Sydri, Galvanic Genius", "Temple Bell", "Temple of the False God", "Tempt with Discovery", "Tempt with Glory", "Tempt with Immortality", "Tempt with Reflections", "Tempt with Vengeance", "Terra Ravager", "Terramorphic Expanse", "Thopter Foundry", "Thornwind Faeries", "Thousand-Year Elixir", "Thraximundar", "Thunderstaff", "Tidal Force", "Tidehollow Strix", "Tooth and Claw", "Tower Gargoyle", "Tower of Fortunes", "Toxic Deluge", "Tranquil Thicket", "Transguild Promenade", "True-Name Nemesis", "Unexpectedly Absent", "Urza's Factory", "Uyo, Silent Prophet", "Valley Rannet", "Vampire Nighthawk", "Vile Requiem", "Viscera Seer", "Viseling", "Vision Skeins", "Vitu-Ghazi, the City-Tree", "Vivid Crag", "Vivid Creek", "Vivid Grove", "Vivid Marsh", "Vizkopa Guildmage", "Walker of the Grove", "Wall of Reverence", "War Cadence", "Warstorm Surge", "Wash Out", "Wayfarer's Bauble", "Well of Lost Dreams", "Where Ancients Tread", "Widespread Panic", "Wight of Precinct Six", "Wild Ricochet", "Winged Coatl", "Witch Hunt", "Wonder", "Wrath of God"});
	}
}
