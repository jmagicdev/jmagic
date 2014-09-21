package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Shadowmoor")
public final class Shadowmoor extends SimpleExpansion
{
	public Shadowmoor()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Island", "Mountain", "Plains", "Swamp");
		this.addCards(Rarity.COMMON, "AEthertow", "Aphotic Wisps", "Apothecary Initiate", "Ashenmoor Cohort", "Ballynock Cohort", "Barkshell Blessing", "Barrenton Cragtreads", "Barrenton Medic", "Blazethorn Scarecrow", "Blight Sickle", "Blistering Dieflyn", "Bloodshed Fever", "Boggart Arsonists", "Briarberry Cohort", "Burn Trail", "Cerulean Wisps", "Chainbreaker", "Cinderbones", "Cinderhaze Wretch", "Consign to Dream", "Crabapple Cohort", "Crimson Wisps", "Cultbrand Cinder", "Curse of Chains", "Devoted Druid", "Disturbing Plot", "Drowner Initiate", "Elsewhere Flask", "Elvish Hexhunter", "Ember Gale", "Emberstrike Duo", "Faerie Macabre", "Farhaven Elf", "Fate Transfer", "Fists of the Demigod", "Foxfire Oak", "Ghastly Discovery", "Giantbaiting", "Gleeful Sabotage", "Gloomlance", "Gloomwidow's Feast", "Goldenglow Moth", "Gravelgill Axeshark", "Gravelgill Duo", "Helm of the Ghastlord", "Hungry Spriggan", "Inescapable Brute", "Inquisitor's Snare", "Intimidator Initiate", "Juvenile Gloomwidow", "Kinscaer Harpoonist", "Kithkin Shielddare", "Last Breath", "Loamdragger Giant", "Loch Korrigan", "Manaforge Cinder", "Manamorphose", "Medicine Runner", "Memory Sluice", "Merrow Wavebreakers", "Mine Excavation", "Morselhoarder", "Mudbrawler Cohort", "Mudbrawler Raiders", "Niveous Wisps", "Nurturer Initiate", "Old Ghastbark", "Oona's Gatewarden", "Parapet Watchers", "Pili-Pala", "Poison the Well", "Power of Fire", "Presence of Gond", "Prismwake Merrow", "Puncture Bolt", "Put Away", "Rattleblaze Scarecrow", "Raven's Run Dragoon", "Rite of Consumption", "Rune-Cervin Rider", "Runes of the Deus", "Rustrazor Butcher", "Safehold Duo", "Safehold Elite", "Safehold Sentry", "Safewright Quest", "Scar", "Scarscale Ritual", "Scrapbasket", "Scuttlemutt", "Scuzzback Marauders", "Scuzzback Scrapper", "Shield of the Oversoul", "Sickle Ripper", "Silkbind Faerie", "Sinking Feeling", "Smash to Smithereens", "Smolder Initiate", "Somnomancer", "Sootstoke Kindler", "Sootwalkers", "Spell Syphon", "Splitting Headache", "Steel of the Godhead", "Strip Bare", "Tattermunge Duo", "Thistledown Duo", "Thornwatch Scarecrow", "Toil to Renown", "Torpor Dust", "Torture", "Traitor's Roar", "Turn to Mist", "Viridescent Wisps", "Wanderbrine Rootcutters", "Watchwing Scarecrow", "Whimwader", "Wildslayer Elves", "Wingrattle Scarecrow", "Woeleecher", "Zealous Guardian");
		this.addCards(Rarity.UNCOMMON, "Advice from the Fae", "Armored Ascension", "Ashenmoor Gouger", "Beseech the Queen", "Biting Tether", "Bloodmark Mentor", "Blowfly Infestation", "Boggart Ram-Gang", "Corrosive Mentor", "Corrupt", "Crowd of Cinders", "Cursecatcher", "Dawnglow Infusion", "Deepchannel Mentor", "Dream Salvage", "Drove of Elves", "Faerie Swarm", "Firespout", "Flame Javelin", "Flourishing Defenses", "Flow of Ideas", "Fossil Find", "Glamer Spinners", "Gloomwidow", "Gnarled Effigy", "Grief Tyrant", "Guttural Response", "Heap Doll", "Hollowsage", "Horde of Boggarts", "Howl of the Night Pack", "Illuminated Folio", "Incremental Blight", "Inkfathom Infiltrator", "Inkfathom Witch", "Jaws of Stone", "Kitchen Finks", "Kithkin Rabble", "Kulrath Knight", "Leech Bonder", "Leechridden Swamp", "Lockjaw Snapper", "Lurebound Scarecrow", "Madblind Mountain", "Mercy Killing", "Merrow Grimeblotter", "Mistmeadow Skulk", "Mistmeadow Witch", "Mistveil Plains", "Moonring Island", "Murderous Redcap", "Pale Wayfarer", "Plumeveil", "Prison Term", "Puresight Merrow", "Pyre Charger", "Raking Canopy", "Reknit", "Repel Intruders", "Resplendent Mentor", "Revelsong Horn", "River's Grasp", "Roughshod Mentor", "Sapseep Forest", "Seedcradle Witch", "Slinking Giant", "Spectral Procession", "Spiteflame Witch", "Tatterkite", "Tattermunge Maniac", "Tattermunge Witch", "Thoughtweft Gambit", "Torrent of Souls", "Tower Above", "Trip Noose", "Umbral Mantle", "Wasp Lancer", "Wicker Warcrawler", "Wild Swing", "Wilt-Leaf Cavaliers");
		this.addCards(Rarity.RARE, "Ashenmoor Liege", "Augury Adept", "Boartusk Liege", "Boon Reflection", "Cauldron of Souls", "Cemetery Puca", "Counterbore", "Cragganwick Cremator", "Deep-Slumber Titan", "Demigod of Revenge", "Deus of Calamity", "Din of the Fireherd", "Dire Undercurrents", "Dramatic Entrance", "Dusk Urchins", "Elemental Mastery", "Enchanted Evening", "Everlasting Torment", "Fire-Lit Thicket", "Fracturing Gust", "Fulminator Mage", "Furystoke Giant", "Ghastlord of Fugue", "Glen Elendra Liege", "Godhead of Awe", "Graven Cairns", "Greater Auramancy", "Grim Poppet", "Heartmender", "Hollowborn Barghest", "Impromptu Raid", "Isleback Spawn", "Knacksaw Clique", "Knollspine Dragon", "Knollspine Invocation", "Mana Reflection", "Mass Calcify", "Memory Plunder", "Midnight Banshee", "Mirrorweave", "Mossbridge Troll", "Mystic Gate", "Oona, Queen of the Fae", "Oracle of Nectars", "Order of Whiteclay", "Oversoul of Dusk", "Painter's Servant", "Plague of Vermin", "Polluted Bonds", "Prismatic Omen", "Puca's Mischief", "Puppeteer Clique", "Rage Reflection", "Reaper King", "Reflecting Pool", "Rhys the Redeemed", "River Kelpie", "Rosheen Meanderer", "Runed Halo", "Savor the Moment", "Spawnwrithe", "Spiteful Visions", "Sunken Ruins", "Swans of Bryn Argoll", "Sygg, River Cutthroat", "Thistledown Liege", "Thought Reflection", "Twilight Shepherd", "Tyrannize", "Valleymaker", "Vexing Shusher", "Wheel of Sun and Moon", "Wilt-Leaf Liege", "Windbrisk Raptor", "Witherscale Wurm", "Wooded Bastion", "Woodfall Primus", "Worldpurge", "Wort, the Raidmother", "Wound Reflection");
	}
}
