package org.rnd.jmagic.engine;

/** Represents the different supertypes. */
public enum SuperType
{
	LEGENDARY
	{
		@Override
		public String toString()
		{
			return "Legendary";
		}
	},
	BASIC
	{
		@Override
		public String toString()
		{
			return "Basic";
		}
	},
	SNOW
	{
		@Override
		public String toString()
		{
			return "Snow";
		}
	},
	WORLD
	{
		@Override
		public String toString()
		{
			return "World";
		}
	}
}
