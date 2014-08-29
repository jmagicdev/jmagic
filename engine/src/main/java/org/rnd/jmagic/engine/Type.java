package org.rnd.jmagic.engine;

/** Represents the different card types. */
public enum Type
{
	// Order is important here!
	TRIBAL
	{
		@Override
		public String toString()
		{
			return "Tribal";
		}
	},
	PLANESWALKER
	{
		@Override
		public String toString()
		{
			return "Planeswalker";
		}
	},
	ARTIFACT
	{
		@Override
		public String toString()
		{
			return "Artifact";
		}
	},
	ENCHANTMENT
	{
		@Override
		public String toString()
		{
			return "Enchantment";
		}
	},
	LAND
	{
		@Override
		public String toString()
		{
			return "Land";
		}
	},
	CREATURE
	{
		@Override
		public String toString()
		{
			return "Creature";
		}
	},
	INSTANT
	{
		@Override
		public String toString()
		{
			return "Instant";
		}
	},
	SORCERY
	{
		@Override
		public String toString()
		{
			return "Sorcery";
		}
	},
	VANGUARD
	{
		@Override
		public boolean isTraditional()
		{
			return false;
		}

		@Override
		public String toString()
		{
			return "Vanguard";
		}
	},
	PLANE
	{
		@Override
		public boolean isTraditional()
		{
			return false;
		}

		@Override
		public String toString()
		{
			return "Plane";
		}
	},
	PHENOMENON
	{
		@Override
		public boolean isTraditional()
		{
			return false;
		}

		@Override
		public String toString()
		{
			return "Phenomenon";
		}
	},
	SCHEME
	{
		@Override
		public boolean isTraditional()
		{
			return false;
		}

		@Override
		public String toString()
		{
			return "Scheme";
		}
	},
	CONSPIRACY
	{
		@Override
		public boolean isTraditional()
		{
			return false;
		}

		@Override
		public String toString()
		{
			return "Conspiracy";
		}
	};


	public static java.util.Set<Type> permanentTypes()
	{
		return java.util.EnumSet.of(ARTIFACT, ENCHANTMENT, LAND, CREATURE, PLANESWALKER);
	}

	public boolean isTraditional()
	{
		return true;
	}
}
