package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Mirrodin")
public final class Mirrodin extends SimpleExpansion
{
	public Mirrodin()
	{
		super(new String[] {"Æther Spellbomb", "Alpha Myr", "Altar of Shadows", "Altar's Light", "Ancient Den", "Annul", "Arc-Slogger", "Arrest", "Assert Authority", "Atog", "Auriok Bladewarden", "Auriok Steelshaper", "Auriok Transfixer", "Awe Strike", "Banshee's Blade", "Barter in Blood", "Battlegrowth", "Betrayal of Flesh", "Blinding Beam", "Blinkmoth Urn", "Blinkmoth Well", "Bloodscent", "Bonesplitter", "Bosh, Iron Golem", "Bottle Gnomes", "Broodstar", "Brown Ouphe", "Cathodion", "Chalice of the Void", "Chimney Imp", "Chromatic Sphere", "Chrome Mox", "Clockwork Beetle", "Clockwork Condor", "Clockwork Dragon", "Clockwork Vorrac", "Cloudpost", "Cobalt Golem", "Confusion in the Ranks", "Consume Spirit", "Contaminated Bond", "Copper Myr", "Copperhoof Vorrac", "Creeping Mold", "Crystal Shard", "Culling Scales", "Damping Matrix", "Dead-Iron Sledge", "Deconstruct", "Detonate", "Disarm", "Disciple of the Vault", "Domineer", "Dragon Blood", "Dream's Grip", "Dross Harvester", "Dross Prowler", "Dross Scorpion", "Duplicant", "Duskworker", "Electrostatic Bolt", "Elf Replica", "Empyrial Plate", "Extraplanar Lens", "Fabricate", "Fangren Hunter", "Farsight Mask", "Fatespinner", "Fiery Gambit", "Fireshrieker", "Fists of the Anvil", "Flayed Nim", "Forest", "Forest", "Forest", "Forest", "Forge Armor", "Fractured Loyalty", "Frogmite", "Galvanic Key", "Gate to the Æther", "Gilded Lotus", "Glimmervoid", "Glissa Sunseeker", "Goblin Charbelcher", "Goblin Dirigible", "Goblin Replica", "Goblin Striker", "Goblin War Wagon", "Gold Myr", "Golem-Skin Gauntlets", "Grab the Reins", "Granite Shard", "Great Furnace", "Grid Monitor", "Grim Reminder", "Groffskithur", "Heartwood Shard", "Hematite Golem", "Hum of the Radix", "Icy Manipulator", "Incite War", "Inertia Bubble", "Iron Myr", "Irradiate", "Island", "Island", "Island", "Island", "Isochron Scepter", "Jinxed Choker", "Journey of Discovery", "Krark's Thumb", "Krark-Clan Grunt", "Krark-Clan Shaman", "Leaden Myr", "Leonin Abunas", "Leonin Bladetrap", "Leonin Den-Guard", "Leonin Elder", "Leonin Scimitar", "Leonin Skyhunter", "Leonin Sun Standard", "Leveler", "Liar's Pendulum", "Lifespark Spellbomb", "Lightning Coils", "Lightning Greaves", "Living Hive", "Lodestone Myr", "Looming Hoverguard", "Loxodon Mender", "Loxodon Peacekeeper", "Loxodon Punisher", "Loxodon Warhammer", "Lumengrid Augur", "Lumengrid Sentinel", "Lumengrid Warden", "Luminous Angel", "Malachite Golem", "March of the Machines", "Mask of Memory", "Mass Hysteria", "Megatog", "Mesmeric Orb", "Mind's Eye", "Mindslaver", "Mindstorm Crown", "Mirror Golem", "Molder Slug", "Molten Rain", "Moriok Scavenger", "Mountain", "Mountain", "Mountain", "Mountain", "Mourner's Shield", "Myr Adapter", "Myr Enforcer", "Myr Incubator", "Myr Mindservant", "Myr Prototype", "Myr Retriever", "Necrogen Mists", "Necrogen Spellbomb", "Needlebug", "Neurok Familiar", "Neurok Hoversail", "Neurok Spy", "Nightmare Lash", "Nim Devourer", "Nim Lasher", "Nim Replica", "Nim Shambler", "Nim Shrieker", "Nuisance Engine", "Oblivion Stone", "Ogre Leadfoot", "Omega Myr", "One Dozen Eyes", "Ornithopter", "Override", "Pearl Shard", "Pentavus", "Pewter Golem", "Plains", "Plains", "Plains", "Plains", "Plated Slagwurm", "Platinum Angel", "Power Conduit", "Predator's Strike", "Promise of Power", "Proteus Staff", "Psychic Membrane", "Psychogenic Probe", "Pyrite Spellbomb", "Quicksilver Elemental", "Quicksilver Fountain", "Raise the Alarm", "Razor Barrier", "Regress", "Reiver Demon", "Relic Bane", "Roar of the Kha", "Rule of Law", "Rust Elemental", "Rustmouth Ogre", "Rustspore Ram", "Scale of Chiss-Goria", "Scrabbling Claws", "Sculpting Steel", "Scythe of the Wretched", "Seat of the Synod", "Second Sunrise", "Seething Song", "Serum Tank", "Shared Fate", "Shatter", "Shrapnel Blast", "Silver Myr", "Skeleton Shard", "Skyhunter Cub", "Skyhunter Patrol", "Slagwurm Armor", "Slith Ascendant", "Slith Bloodletter", "Slith Firewalker", "Slith Predator", "Slith Strider", "Solar Tide", "Soldier Replica", "Solemn Simulacrum", "Somber Hoverguard", "Soul Foundry", "Soul Nova", "Spellweaver Helix", "Sphere of Purity", "Spikeshot Goblin", "Spoils of the Vault", "Stalking Stones", "Steel Wall", "Sun Droplet", "Sunbeam Spellbomb", "Swamp", "Swamp", "Swamp", "Swamp", "Sword of Kaldra", "Sylvan Scrying", "Synod Sanctum", "Taj-Nar Swordsmith", "Talisman of Dominance", "Talisman of Impulse", "Talisman of Indulgence", "Talisman of Progress", "Talisman of Unity", "Tanglebloom", "Tangleroot", "Tel-Jilad Archers", "Tel-Jilad Chosen", "Tel-Jilad Exile", "Tel-Jilad Stylus", "Tempest of Light", "Temporal Cascade", "Terror", "Thirst for Knowledge", "Thought Prison", "Thoughtcast", "Timesifter", "Titanium Golem", "Tooth and Nail", "Tooth of Chiss-Goria", "Tower of Champions", "Tower of Eons", "Tower of Fortunes", "Tower of Murmurs", "Trash for Treasure", "Tree of Tales", "Triskelion", "Troll Ascetic", "Trolls of Tel-Jilad", "Turn to Dust", "Vault of Whispers", "Vedalken Archmage", "Vermiculos", "Viridian Joiner", "Viridian Longbow", "Viridian Shaman", "Vorrac Battlehorns", "Vulshok Battlegear", "Vulshok Battlemaster", "Vulshok Berserker", "Vulshok Gauntlets", "Wail of the Nim", "Wall of Blood", "Wanderguard Sentry", "War Elemental", "Welding Jar", "Wizard Replica", "Woebearer", "Worldslayer", "Wrench Mind", "Wurmskin Forger", "Yotian Soldier"});
	}
}
