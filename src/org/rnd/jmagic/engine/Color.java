package org.rnd.jmagic.engine;

/**
 * Represents one of the five colors of Magic.
 */
public enum Color implements Colorful
{
	WHITE(SubType.PLAINS, "W", "white"), //
	BLUE(SubType.ISLAND, "U", "blue"), //
	BLACK(SubType.SWAMP, "B", "black"), //
	RED(SubType.MOUNTAIN, "R", "red"), //
	GREEN(SubType.FOREST, "G", "green"); //

	public static java.util.Set<Color> allColors()
	{
		return java.util.EnumSet.allOf(Color.class);
	}

	private final SubType landType;
	private final String letter;

	private final String string;

	Color(SubType landType, String letter, String string)
	{
		this.landType = landType;
		this.letter = letter;
		this.string = string;
	}

	public Color[] getAllies()
	{
		return new Color[] {this.getPrev(), this.getNext()};
	}

	/**
	 * @return The basic land type associated with this color.
	 */
	public final SubType getBasicLandType()
	{
		return this.landType;
	}

	/** @return This color. */
	@Override
	public java.util.Set<Color> getColors()
	{
		return java.util.Collections.singleton(this);
	}

	public Color[] getEnemies()
	{
		Color firstEnemy = this.getNext().getNext();
		return new Color[] {firstEnemy, firstEnemy.getNext()};
	}

	/**
	 * Gets the letter associated with this color.
	 * 
	 * @return "W" for white, "U" for blue, "B" for black, "R" for red, "G" for
	 * green.
	 */
	public final String getLetter()
	{
		return this.letter;
	}

	/**
	 * @return A ManaType object representing mana of this color.
	 */
	public final ManaSymbol.ManaType getManaType()
	{
		for(ManaSymbol.ManaType type: ManaSymbol.ManaType.values())
			if(type.name().equals(this.name()))
				return type;
		throw new InternalError("ManaType not found for " + this.name() + ".getManaType().");
	}

	@Override
	public final java.util.Set<ManaSymbol.ManaType> getManaTypes()
	{
		return java.util.Collections.singleton(this.getManaType());
	}

	private Color getNext()
	{
		Color[] values = Color.values();
		return Color.values()[(this.ordinal() + 1) % values.length];
	}

	private Color getPrev()
	{
		Color[] values = Color.values();
		return Color.values()[(this.ordinal() + (values.length - 1)) % values.length];
	}

	@Override
	public final String toString()
	{
		return this.string;
	}

	public static Color getColorForType(SubType landType)
	{
		if(landType == SubType.PLAINS)
			return Color.WHITE;
		if(landType == SubType.ISLAND)
			return Color.BLUE;
		if(landType == SubType.SWAMP)
			return Color.BLACK;
		if(landType == SubType.MOUNTAIN)
			return Color.RED;
		if(landType == SubType.FOREST)
			return Color.GREEN;
		return null;
	}
}
