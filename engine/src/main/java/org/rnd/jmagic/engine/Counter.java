package org.rnd.jmagic.engine;

/**
 * Represents a counter (the noun).
 */
public class Counter implements java.io.Serializable, Comparable<Counter>
{
	/**
	 * Represents the different types of counters.
	 */
	public enum CounterType
	{
		MINUS_ONE_MINUS_ONE
		{
			@Override
			public void modifyObject(GameObject object)
			{
				object.setPower(object.getPower() - 1);
				object.setToughness(object.getToughness() - 1);
			}

			@Override
			public String toString()
			{
				return "-1/-1 counter";
			}
		},
		MINUS_TWO_MINUS_ONE
		{
			@Override
			public void modifyObject(GameObject object)
			{
				object.setPower(object.getPower() - 2);
				object.setToughness(object.getToughness() - 1);
			}

			@Override
			public String toString()
			{
				return "-2/-1 counter";
			}
		},
		MINUS_TWO_MINUS_TWO
		{
			@Override
			public void modifyObject(GameObject object)
			{
				object.setPower(object.getPower() - 2);
				object.setToughness(object.getToughness() - 2);
			}

			@Override
			public String toString()
			{
				return "-2/-2 counter";
			}
		},
		MINUS_ZERO_MINUS_ONE
		{
			@Override
			public void modifyObject(GameObject object)
			{
				// object.power -= 0;
				object.setToughness(object.getToughness() - 1);
			}

			@Override
			public String toString()
			{
				return "-0/-1 counter";
			}
		},
		MINUS_ZERO_MINUS_TWO
		{
			@Override
			public void modifyObject(GameObject object)
			{
				// object.power -= 0;
				object.setToughness(object.getToughness() - 2);
			}

			@Override
			public String toString()
			{
				return "-0/-2 counter";
			}
		},
		PLUS_ONE_PLUS_ONE
		{
			@Override
			public void modifyObject(GameObject object)
			{
				object.setPower(object.getPower() + 1);
				object.setToughness(object.getToughness() + 1);
			}

			@Override
			public String toString()
			{
				return "+1/+1 counter";
			}
		},
		PLUS_ONE_PLUS_TWO
		{
			@Override
			public void modifyObject(GameObject object)
			{
				object.setPower(object.getPower() + 1);
				object.setToughness(object.getToughness() + 2);
			}

			@Override
			public String toString()
			{
				return "+1/+2 counter";
			}
		},
		PLUS_ONE_PLUS_ZERO
		{
			@Override
			public void modifyObject(GameObject object)
			{
				object.setPower(object.getPower() + 1);
				// object.toughness += 0;
			}

			@Override
			public String toString()
			{
				return "+1/+0 counter";
			}
		},
		PLUS_TWO_PLUS_TWO
		{
			@Override
			public void modifyObject(GameObject object)
			{
				object.setPower(object.getPower() + 2);
				object.setToughness(object.getToughness() + 2);
			}

			@Override
			public String toString()
			{
				return "+2/+2 counter";
			}
		},
		PLUS_TWO_PLUS_ZERO
		{
			@Override
			public void modifyObject(GameObject object)
			{
				object.setPower(object.getPower() + 2);
				// object.toughness += 0;
			}

			@Override
			public String toString()
			{
				return "+2/+0 counter";
			}
		},
		PLUS_ZERO_PLUS_ONE
		{
			@Override
			public void modifyObject(GameObject object)
			{
				// object.power += 0;
				object.setToughness(object.getToughness() + 1);
			}

			@Override
			public String toString()
			{
				return "+0/+1 counter";
			}
		},
		PLUS_ZERO_PLUS_TWO
		{
			@Override
			public void modifyObject(GameObject object)
			{
				// object.power += 0;
				object.setToughness(object.getToughness() + 2);
			}

			@Override
			public String toString()
			{
				return "+0/+2 counter";
			}
		},
		AGE, AIM, ARROW, ARROWHEAD, AWAKENING, BLAZE, BLOOD, BOUNTY, CARRION, CHARGE, CORPSE, CREDIT, CUBE, CURRENCY, DEATH, DELAY, DEPLETION, DESPAIR, DEVOTION, DIVINITY, DOOM, DREAM, ECHO, ELIXIR, ENERGY, EON, EYEBALL, FADE, FATE, FEATHER, FILIBUSTER, FLAME, FLOOD, FUNGUS, FUSE, GLYPH, GOLD, GROWTH, HATCHLING, HEALING, HOOFPRINT, HOURGLASS, HUNGER, ICE, INCUBATION, INFECTION, INTERVENTION, JAVELIN, KI, LEVEL, LORE, LOYALTY, LUCK, MAGNET, MANIFESTATION, MANNEQUIN, MASK, MATRIX, MINE, MINING, MIRE, MUSTER, NET, OMEN, ORE, PAGE, PAIN, PARALYZATION, PETAL, PETRIFICATION, PHYLACTERY, PIN, PLAGUE, POISON, POLYP, PRESSURE, PUPA, QUEST, RUST, SCREAM, SHELL, SHIELD, SHRED, SLEEP, SLEIGHT, SLIME, SOOT, SPORE, STORAGE, STUDY, THEFT, TIDE, TIME, TOWER, TRAINING, TRAP, TREASURE, VELOCITY, VERSE, VITALITY, WAGE, WINCH, WIND, WISH;

		/**
		 * Uses a counter of this type to modify an object.
		 * 
		 * @param object The object to modify.
		 */
		public void modifyObject(GameObject object)
		{
			// Intentionally left blank, since most counters have no effect by
			// themselves
		}

		/**
		 * @return A string representation of this counter.
		 */
		@Override
		public String toString()
		{
			return super.toString().toLowerCase() + " counter";
		}
	}

	private static final long serialVersionUID = 1L;

	private final CounterType type;
	public final int sourceID;

	/**
	 * Constructs a counter of the given type.
	 * 
	 * @param type What kind of counter to create.
	 * @param sourceID What permanent/player this counter is on.
	 */
	public Counter(CounterType type, int sourceID)
	{
		this.type = type;
		this.sourceID = sourceID;
	}

	/**
	 * @return What kind of counter this is.
	 */
	public CounterType getType()
	{
		return this.type;
	}

	/**
	 * Uses this counter to modify an object (only applicable for counters that
	 * change a creature's power/toughness).
	 * 
	 * @param object The object to modify.
	 */
	public void modifyObject(GameObject object)
	{
		this.type.modifyObject(object);
	}

	@Override
	public int compareTo(Counter other)
	{
		int typeCompare = this.type.compareTo(other.type);
		if(typeCompare == 0)
			return this.sourceID - other.sourceID;
		return typeCompare;
	}

	@Override
	public java.lang.String toString()
	{
		return this.type.toString();
	}
}
