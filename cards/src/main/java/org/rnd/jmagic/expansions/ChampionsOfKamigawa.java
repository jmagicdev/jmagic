package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Champions of Kamigawa")
public final class ChampionsOfKamigawa extends SimpleExpansion
{
	public ChampionsOfKamigawa()
	{
		super(new String[] {"Akki Avalanchers", "Akki Coalflinger", "Akki Lavarunner", "Akki Rockspeaker", "Akki Underminer", "Ashen-Skin Zubera", "Aura of Dominion", "Azami, Lady of Scrolls", "Azusa, Lost but Seeking", "Battle-Mad Ronin", "Befoul", "Ben-Ben, Akki Hermit", "Blessed Breath", "Blind with Anger", "Blood Rites", "Blood Speaker", "Bloodthirsty Ogre", "Boseiju, Who Shelters All", "Brothers Yamazaki", "Brothers Yamazaki", "Brutal Deceiver", "Budoka Gardener", "Burr Grafter", "Bushi Tenderfoot", "Cage of Hands", "Call to Glory", "Callous Deceiver", "Candles' Glow", "Cleanfall", "Cloudcrest Lake", "Commune with Nature", "Consuming Vortex", "Counsel of the Soratami", "Cranial Extraction", "Cruel Deceiver", "Crushing Pain", "Cursed Ronin", "Cut the Tethers", "Dampen Thought", "Dance of Shadows", "Deathcurse Ogre", "Desperate Ritual", "Devoted Retainer", "Devouring Greed", "Devouring Rage", "Distress", "Dosan the Falling Leaf", "Dripping-Tongue Zubera", "Earthshaker", "Eerie Procession", "Eiganjo Castle", "Eight-and-a-Half-Tails", "Ember-Fist Zubera", "Ethereal Haze", "Eye of Nowhere", "Feast of Worms", "Feral Deceiver", "Field of Reality", "Floating-Dream Zubera", "Forbidden Orchard", "Forest", "Forest", "Forest", "Forest", "Frostwielder", "Gale Force", "General's Kabuto", "Ghostly Prison", "Gibbering Kami", "Gifts Ungiven", "Glacial Ray", "Glimpse of Nature", "Godo, Bandit Warlord", "Graceful Adept", "Guardian of Solitude", "Gutwrencher Oni", "Hair-Strung Koto", "Hall of the Bandit Lord", "Hana Kami", "Hanabi Blast", "Hankyu", "Harsh Deceiver", "He Who Hungers", "Heartbeat of Spring", "Hearth Kami", "Hideous Laughter", "Hikari, Twilight Guardian", "Hinder", "Hisoka's Defiance", "Hisoka's Guard", "Hisoka, Minamo Sensei", "Hold the Line", "Honden of Cleansing Fire", "Honden of Infinite Rage", "Honden of Life's Web", "Honden of Night's Reach", "Honden of Seeing Winds", "Honor-Worn Shaku", "Horizon Seed", "Horobi, Death's Wail", "Humble Budoka", "Hundred-Talon Kami", "Imi Statue", "Iname, Death Aspect", "Iname, Life Aspect", "Indomitable Will", "Initiate of Blood", "Innocence Kami", "Isamaru, Hound of Konda", "Island", "Island", "Island", "Island", "Jade Idol", "Journeyer's Kite", "Joyous Respite", "Jugan, the Rising Star", "Jukai Messenger", "Junkyo Bell", "Jushi Apprentice", "Kabuto Moth", "Kami of Ancient Law", "Kami of Fire's Roar", "Kami of Lunacy", "Kami of Old Stone", "Kami of the Hunt", "Kami of the Painted Road", "Kami of the Palace Fields", "Kami of the Waning Moon", "Kami of Twisted Reflection", "Kashi-Tribe Reaver", "Kashi-Tribe Warriors", "Keiga, the Tide Star", "Kiki-Jiki, Mirror Breaker", "Kiku, Night's Flower", "Kitsune Blademaster", "Kitsune Diviner", "Kitsune Healer", "Kitsune Mystic", "Kitsune Riftwalker", "Kodama of the North Tree", "Kodama of the South Tree", "Kodama's Might", "Kodama's Reach", "Kokusho, the Evening Star", "Konda's Banner", "Konda's Hatamoto", "Konda, Lord of Eiganjo", "Kumano's Pupils", "Kumano, Master Yamabushi", "Kuro, Pitlord", "Kusari-Gama", "Lantern Kami", "Lantern-Lit Graveyard", "Lava Spike", "Lifted by Clouds", "Long-Forgotten Gohei", "Lure", "Mana Seism", "Marrow-Gnawer", "Masako the Humorless", "Matsu-Tribe Decoy", "Meloku the Clouded Mirror", "Midnight Covenant", "Minamo, School at Water's Edge", "Mindblaze", "Moonring Mirror", "Moss Kami", "Mothrider Samurai", "Mountain", "Mountain", "Mountain", "Mountain", "Myojin of Cleansing Fire", "Myojin of Infinite Rage", "Myojin of Life's Web", "Myojin of Night's Reach", "Myojin of Seeing Winds", "Mystic Restraints", "Nagao, Bound by Honor", "Nature's Will", "Nezumi Bone-Reader", "Nezumi Cutthroat", "Nezumi Graverobber", "Nezumi Ronin", "Nezumi Shortfang", "Night Dealings", "Night of Souls' Betrayal", "Nine-Ringed Bo", "No-Dachi", "Numai Outcast", "Oathkeeper, Takeno's Daisho", "Okina, Temple to the Grandfathers", "Oni Possession", "Orbweaver Kumo", "Order of the Sacred Bell", "Ore Gorger", "Orochi Eggwatcher", "Orochi Hatchery", "Orochi Leafcaller", "Orochi Ranger", "Orochi Sustainer", "Otherworldly Journey", "Pain Kami", "Painwracker Oni", "Part the Veil", "Peer Through Depths", "Petals of Insight", "Pinecrest Ridge", "Pious Kitsune", "Plains", "Plains", "Plains", "Plains", "Psychic Puppetry", "Pull Under", "Quiet Purity", "Rag Dealer", "Ragged Veins", "Reach Through Mists", "Reciprocate", "Reito Lantern", "Rend Flesh", "Rend Spirit", "Reverse the Sands", "Reweave", "River Kaijin", "Ronin Houndmaster", "Rootrunner", "Ryusei, the Falling Star", "Sachi, Daughter of Seshiro", "Sakura-Tribe Elder", "Samurai Enforcers", "Samurai of the Pale Curtain", "Scuttling Death", "Seizan, Perverter of Truth", "Sensei Golden-Tail", "Sensei's Divining Top", "Serpent Skin", "Seshiro the Anointed", "Shell of the Last Kappa", "Shimatsu the Bloodcloaked", "Shinka, the Bloodsoaked Keep", "Shisato, Whispering Hunter", "Shizo, Death's Storehouse", "Sideswipe", "Sift Through Sands", "Silent-Chant Zubera", "Sire of the Storm", "Soilshaper", "Sokenzan Bruiser", "Soratami Cloudskater", "Soratami Mirror-Guard", "Soratami Mirror-Mage", "Soratami Rainshaper", "Soratami Savant", "Soratami Seer", "Sosuke, Son of Seshiro", "Soul of Magma", "Soulblast", "Soulless Revival", "Squelch", "Stone Rain", "Strange Inversion", "Strength of Cedars", "Struggle for Sanity", "Student of Elements", "Swallowing Plague", "Swamp", "Swamp", "Swamp", "Swamp", "Swirl the Mists", "Takeno, Samurai General", "Tatsumasa, the Dragon's Fang", "Teller of Tales", "Tenza, Godo's Maul", "Terashi's Cry", "The Unspeakable", "Thief of Hope", "Thoughtbind", "Thousand-legged Kami", "Through the Breach", "Tide of War", "Time of Need", "Time Stop", "Tranquil Garden", "Uba Mask", "Uncontrollable Anger", "Unearthly Blizzard", "Unnatural Speed", "Untaidake, the Cloud Keeper", "Uyo, Silent Prophet", "Vassal's Duty", "Venerable Kumo", "Vigilance", "Villainous Ogre", "Vine Kami", "Waking Nightmare", "Wandering Ones", "Waterveil Cavern", "Wear Away", "Wicked Akuba", "Yamabushi's Flame", "Yamabushi's Storm", "Yosei, the Morning Star", "Zo-Zu the Punisher"});
	}
}
