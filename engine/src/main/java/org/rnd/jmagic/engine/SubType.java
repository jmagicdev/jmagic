package org.rnd.jmagic.engine;

public enum SubType
{
	// ***Creature Types***
	// Ordered based on how Wizards seems to order creature types on their
	// cards. Dauthi Mercenary and Mercenary Knight form a loop between the
	// Knight and Mercenary creature types... but other than that I'm pretty
	// sure this is right. -RulesGuru
	// These are first because of Tribal Artifacts and Tribal Enchantments

	// PRE-RACE
	ELDER(Category.CREATURE), ELEMENTAL(Category.CREATURE), GRAVEBORN(Category.CREATURE), HARPY(Category.CREATURE), HELLION(Category.CREATURE), HUMAN(Category.CREATURE), NAUTILUS(Category.CREATURE), NIGHTMARE(Category.CREATURE), NIGHTSTALKER(Category.CREATURE), ZOMBIE(Category.CREATURE),
	// RACE
	// "Snake Elf", "Insect Beast"
	SNAKE(Category.CREATURE), INSECT(Category.CREATURE),
	//
	ANGEL(Category.CREATURE), ANTEATER(Category.CREATURE), ANTELOPE(Category.CREATURE), APE(Category.CREATURE), ATOG(Category.CREATURE), AUROCHS(Category.CREATURE), BADGER(Category.CREATURE), BASILISK(Category.CREATURE), BAT(Category.CREATURE), BEAR(Category.CREATURE), BEAST(Category.CREATURE), BEEBLE(Category.CREATURE), BIRD(Category.CREATURE), BLINKMOTH(Category.CREATURE), BOAR(Category.CREATURE), BRINGER(Category.CREATURE), BRUSHWAGG(Category.CREATURE), CAMARID(Category.CREATURE), CAMEL(Category.CREATURE), CARIBOU(Category.CREATURE), CAT(Category.CREATURE), CENTAUR(Category.CREATURE), CEPHALID(Category.CREATURE), CHIMERA(Category.CREATURE), COCKATRICE(Category.CREATURE), CRAB(Category.CREATURE), CROCODILE(Category.CREATURE), CYCLOPS(Category.CREATURE), DAUTHI(Category.CREATURE), DEMON(Category.CREATURE), DEVIL(Category.CREATURE), DJINN(Category.CREATURE), DRAGON(Category.CREATURE), DRAKE(Category.CREATURE), DREADNOUGHT(Category.CREATURE), DRYAD(Category.CREATURE), DWARF(Category.CREATURE), EFREET(Category.CREATURE), EGG(Category.CREATURE), ELDRAZI(Category.CREATURE), ELEPHANT(Category.CREATURE), ELF(Category.CREATURE), ELK(Category.CREATURE), EYE(Category.CREATURE), FAERIE(Category.CREATURE), FERRET(Category.CREATURE), FISH(Category.CREATURE), FOX(Category.CREATURE), FROG(Category.CREATURE), GARGOYLE(Category.CREATURE), GNOME(Category.CREATURE), GOAT(Category.CREATURE), GOBLIN(Category.CREATURE), GOLEM(Category.CREATURE), GORGON(Category.CREATURE), GREMLIN(Category.CREATURE), GRIFFIN(Category.CREATURE), HIPPO(Category.CREATURE), HIPPOGRIFF(Category.CREATURE), HOMARID(Category.CREATURE), HOMUNCULUS(Category.CREATURE), HORSE(Category.CREATURE), HOUND(Category.CREATURE), HYDRA(Category.CREATURE), HYENA(Category.CREATURE), IMP(Category.CREATURE), JELLYFISH(Category.CREATURE), KAVU(Category.CREATURE), KIRIN(Category.CREATURE), KITHKIN(Category.CREATURE), KOBOLD(Category.CREATURE), KOR(Category.CREATURE), KRAKEN(Category.CREATURE), LAMMASU(Category.CREATURE), LEECH(Category.CREATURE), LEVIATHAN(Category.CREATURE), LHURGOYF(Category.CREATURE), LICID(Category.CREATURE), LIZARD(Category.CREATURE), MANTICORE(Category.CREATURE), MASTICORE(Category.CREATURE), MERFOLK(Category.CREATURE), METATHRAN(Category.CREATURE), MINOTAUR(Category.CREATURE), MONGOOSE(Category.CREATURE), MOONFOLK(Category.CREATURE), MYR(Category.CREATURE), NEPHILIM(Category.CREATURE), NOGGLE(Category.CREATURE), OCTOPUS(Category.CREATURE), OGRE(Category.CREATURE), OOZE(Category.CREATURE), ORC(Category.CREATURE), ORGG(Category.CREATURE), OUPHE(Category.CREATURE), OX(Category.CREATURE), OYSTER(Category.CREATURE), PEGASUS(Category.CREATURE), PHELDDAGRIF(Category.CREATURE), PHOENIX(Category.CREATURE), PLANT(Category.CREATURE), RABBIT(Category.CREATURE), RAT(Category.CREATURE), RHINO(Category.CREATURE), SALAMANDER(Category.CREATURE), SAPROLING(Category.CREATURE), SATYR(Category.CREATURE), SCORPION(Category.CREATURE), SERPENT(Category.CREATURE), SHAPESHIFTER(Category.CREATURE), SHEEP(Category.CREATURE), SIREN(Category.CREATURE), SLITH(Category.CREATURE), SLIVER(Category.CREATURE), SLUG(Category.CREATURE), SOLTARI(Category.CREATURE), SPECTER(Category.CREATURE), SPHINX(Category.CREATURE), SPIDER(Category.CREATURE), SPIKE(Category.CREATURE), SQUID(Category.CREATURE), SQUIRREL(Category.CREATURE), STARFISH(Category.CREATURE), SURRAKAR(Category.CREATURE), THALAKOS(Category.CREATURE), THRULL(Category.CREATURE), TREEFOLK(Category.CREATURE), TURTLE(Category.CREATURE), UNICORN(Category.CREATURE), VAMPIRE(Category.CREATURE), VEDALKEN(Category.CREATURE), VIASHINO(Category.CREATURE), VOLVER(Category.CREATURE), WHALE(Category.CREATURE), WOLF(Category.CREATURE), WOLVERINE(Category.CREATURE), WOMBAT(Category.CREATURE), WORM(Category.CREATURE), WRAITH(Category.CREATURE), WURM(Category.CREATURE), YETI(Category.CREATURE), ZUBERA(Category.CREATURE),
	// POST-RACE
	// "Skeleton Troll"(Category.CREATURE), "Skeleton Wall"
	SKELETON(Category.CREATURE), TROLL(Category.CREATURE), WALL(Category.CREATURE),
	// "Insect Shade"(Category.CREATURE), "Shade Spirit"
	SHADE(Category.CREATURE),
	// "Spirit Nomad"
	SPIRIT(Category.CREATURE),
	// "Nomad Giant"
	NOMAD(Category.CREATURE),
	//
	DRONE(Category.CREATURE), GIANT(Category.CREATURE), HAG(Category.CREATURE), ILLUSION(Category.CREATURE), SCARECROW(Category.CREATURE), FUNGUS(Category.CREATURE),
	// "Spirit Horror"
	HORROR(Category.CREATURE),

	// PRE-CLASS
	MONK(Category.CREATURE), REBEL(Category.CREATURE), PIRATE(Category.CREATURE), ROGUE(Category.CREATURE), SPELLSHAPER(Category.CREATURE),
	// CLASS
	ADVISOR(Category.CREATURE), ARTIFICER(Category.CREATURE), ASSEMBLY_WORKER(Category.CREATURE), BARBARIAN(Category.CREATURE), CITIZEN(Category.CREATURE), CLERIC(Category.CREATURE), NINJA(Category.CREATURE), SAMURAI(Category.CREATURE), SERF(Category.CREATURE), SOLDIER(Category.CREATURE), SURVIVOR(Category.CREATURE),
	// "Soldier Warrior",
	// "Warrior Scout",
	// "Warrior Knight", "Warrior Druid"
	WARRIOR(Category.CREATURE),
	// "Druid Scout Archer"
	DRUID(Category.CREATURE), SCOUT(Category.CREATURE),
	// POST-CLASS
	BERSERKER(Category.CREATURE), FLAGBEARER(Category.CREATURE), MINION(Category.CREATURE), MONGER(Category.CREATURE), MYSTIC(Category.CREATURE), RIGGER(Category.CREATURE), ARCHER(Category.CREATURE), SHAMAN(Category.CREATURE), MERCENARY(Category.CREATURE), KNIGHT(Category.CREATURE), WIZARD(Category.CREATURE), MUTANT(Category.CREATURE),
	// "Mercenary Assassin",
	// "Shaman Ally",
	// "Berserker Ally", "Wizard Ally"
	ASSASSIN(Category.CREATURE), ALLY(Category.CREATURE),

	// Weird stuff
	ARCHON(Category.CREATURE), AVATAR(Category.CREATURE), CARRIER(Category.CREATURE), CONSTRUCT(Category.CREATURE), COWARD(Category.CREATURE), DESERTER(Category.CREATURE), GERM(Category.CREATURE), INCARNATION(Category.CREATURE), JUGGERNAUT(Category.CREATURE), ORB(Category.CREATURE), PENTAVITE(Category.CREATURE), PEST(Category.CREATURE), PINCHER(Category.CREATURE), PRAETOR(Category.CREATURE), PRISM(Category.CREATURE), REFLECTION(Category.CREATURE), SAND(Category.CREATURE), SPAWN(Category.CREATURE), SPLINTER(Category.CREATURE), SPONGE(Category.CREATURE), TETRAVITE(Category.CREATURE), THOPTER(Category.CREATURE), TRISKELAVITE(Category.CREATURE), WEIRD(Category.CREATURE), WEREWOLF(Category.CREATURE),

	// ***Planeswalker Types***
	AJANI(Category.PLANESWALKER), //
	BOLAS(Category.PLANESWALKER), //
	CHANDRA(Category.PLANESWALKER), //
	DOMRI(Category.PLANESWALKER), //
	ELSPETH(Category.PLANESWALKER), //
	GARRUK(Category.PLANESWALKER), //
	GIDEON(Category.PLANESWALKER), //
	JACE(Category.PLANESWALKER), //
	KARN(Category.PLANESWALKER), //
	KOTH(Category.PLANESWALKER), //
	LILIANA(Category.PLANESWALKER), //
	NISSA(Category.PLANESWALKER), //
	SARKHAN(Category.PLANESWALKER), //
	SORIN(Category.PLANESWALKER), //
	TAMIYO(Category.PLANESWALKER), //
	TIBALT(Category.PLANESWALKER), //
	TEZZERET(Category.PLANESWALKER), //
	VENSER(Category.PLANESWALKER), //
	VRASKA(Category.PLANESWALKER), //

	// ***Artifact Types***
	CONTRAPTION(Category.ARTIFACT), EQUIPMENT(Category.ARTIFACT), FORTIFICATION(Category.ARTIFACT),

	// ***Enchantment Types***
	AURA(Category.ENCHANTMENT), CURSE(Category.ENCHANTMENT), SHRINE(Category.ENCHANTMENT),

	// ***Land Types***
	// order is important for display purposes here
	URZAS(Category.LAND)
	{
		@Override
		public String toString()
		{
			return "Urza's";
		}
	},
	// Urza's specific land types
	POWER_PLANT(Category.LAND), MINE(Category.LAND), TOWER(Category.LAND),
	// Non-basic non-Urza land types
	DESERT(Category.LAND), GATE(Category.LAND), LAIR(Category.LAND), LOCUS(Category.LAND),
	// Basic land types
	PLAINS(Category.LAND), ISLAND(Category.LAND), SWAMP(Category.LAND), MOUNTAIN(Category.LAND), FOREST(Category.LAND),

	// ***Spell Types***
	ARCANE(Category.SPELL), TRAP(Category.SPELL),

	// ***Plane Types***
	ALARA(Category.PLANE), //
	ARKHOS(Category.PLANE), //
	BOLASS_MEDITATION_REALM(Category.PLANE)
	{
		@Override
		public String toString()
		{
			return "Bolas's Meditation Realm";
		}
	}, //
	DOMINARIA(Category.PLANE), //
	EQUILOR(Category.PLANE), //
	IQUATANA(Category.PLANE), //
	IR(Category.PLANE), //
	KALDHEIM(Category.PLANE), //
	KAMIGAWA(Category.PLANE), //
	KARSUS(Category.PLANE), //
	LORWYN(Category.PLANE), //
	LUVION(Category.PLANE), //
	MERCADIA(Category.PLANE), //
	MIRRODIN(Category.PLANE), //
	MOAG(Category.PLANE), //
	MURAGANDA(Category.PLANE), //
	PHYREXIA(Category.PLANE), //
	PYRULEA(Category.PLANE), //
	RABIAH(Category.PLANE), //
	RATH(Category.PLANE), //
	RAVNICA(Category.PLANE), //
	SEGOVIA(Category.PLANE), //
	SERRAS_REALM(Category.PLANE)
	{
		@Override
		public String toString()
		{
			return "Serra's Realm";
		}
	}, //
	SHADOWMOOR(Category.PLANE), //
	SHANDALAR(Category.PLANE), //
	ULGROTHA(Category.PLANE), //
	VALLA(Category.PLANE), //
	WILDFIRE(Category.PLANE), //
	ZENDIKAR(Category.PLANE);

	private final Category category;

	private SubType(Category category)
	{
		this.category = category;
	}

	public java.util.List<Type> getTypes()
	{
		return this.category.types;
	}

	@Override
	public String toString()
	{
		return org.rnd.util.CamelCase.enumValueToDisplay(this.name());
	}

	/**
	 * This class encapsulates the Type[] member of sub types to simplify the
	 * SubType constructor, and to allow the return values of SubType.getTypes()
	 * to be reference-comparable.
	 */
	private enum Category
	{
		ARTIFACT(Type.ARTIFACT), CREATURE(Type.CREATURE, Type.TRIBAL), ENCHANTMENT(Type.ENCHANTMENT), LAND(Type.LAND), PLANESWALKER(Type.PLANESWALKER), SPELL(Type.INSTANT, Type.SORCERY), PLANE(Type.PLANE);

		// Use a List instead of an Array so we can enforce immutability while
		// retaining the ability to compare by reference.
		public final java.util.List<Type> types;

		Category(Type... types)
		{
			this.types = java.util.Collections.unmodifiableList(java.util.Arrays.asList(types));
		}

		public static Category getCategory(Type type)
		{
			for(Category category: Category.values())
				if(category.types.contains(type))
					return category;
			return null;
		}
	}

	private static java.util.Map<Category, java.util.Set<SubType>> map = null;

	public static java.util.Set<SubType> getAllSubTypesFor(Type type)
	{
		synchronized(SubType.class)
		{
			if(map == null)
			{
				map = new java.util.EnumMap<Category, java.util.Set<SubType>>(Category.class);
				for(Category category: Category.values())
					map.put(category, java.util.EnumSet.noneOf(SubType.class));
				for(SubType subType: SubType.values())
					map.get(subType.category).add(subType);
				for(java.util.Map.Entry<Category, java.util.Set<SubType>> entry: map.entrySet())
					entry.setValue(java.util.Collections.unmodifiableSet(entry.getValue()));
				map = java.util.Collections.unmodifiableMap(map);
			}
		}

		return map.get(Category.getCategory(type));
	}

	public static java.util.Set<SubType> getSubTypesFor(Type type, java.util.Set<SubType> subTypes)
	{
		Category cat = Category.getCategory(type);
		java.util.Set<SubType> ret = new java.util.HashSet<SubType>();
		for(SubType subType: subTypes)
			if(subType.category == cat)
				ret.add(subType);
		return ret;
	}

	public static java.util.Set<SubType> getBasicLandTypes()
	{
		return java.util.EnumSet.of(SubType.PLAINS, SubType.ISLAND, SubType.SWAMP, SubType.MOUNTAIN, SubType.FOREST);
	}

	// TODO: inline this...
	public static java.util.Set<SubType> getAllCreatureTypes()
	{
		return getAllSubTypesFor(Type.CREATURE);
	}
}
