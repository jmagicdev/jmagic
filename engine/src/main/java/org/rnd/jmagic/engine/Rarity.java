package org.rnd.jmagic.engine;

public enum Rarity
{
	LAND("L"), COMMON("C"), UNCOMMON("U"), RARE("R"), MYTHIC("M"), SPECIAL("S");

	private String toString;

	Rarity(String name)
	{
		this.toString = name;
	}

	@Override
	public String toString()
	{
		return this.toString;
	}
}
