package org.rnd.jmagic.engine;

/** Represents one mana symbol. */
public class ManaSymbol implements Colorful, Comparable<ManaSymbol>, java.io.Serializable
{
	/** Represents the different types of mana. */
	public enum ManaType implements Colorful
	{
		BLACK(Color.BLACK), BLUE(Color.BLUE), GREEN(Color.GREEN), RED(Color.RED), WHITE(Color.WHITE), COLORLESS(null)
		{
			@Override
			public Color getColor()
			{
				throw new InternalError("Calling getColor() on COLORLESS ManaType");
			}
		};

		private Color color;

		ManaType(Color color)
		{
			this.color = color;
		}

		/** @return The color associated with this mana type. */
		public Color getColor()
		{
			return this.color;
		}

		@Override
		public java.util.Set<Color> getColors()
		{
			return java.util.Collections.singleton(this.color);
		}

		/** @return This mana type. */
		@Override
		public java.util.Set<ManaType> getManaTypes()
		{
			return java.util.Collections.singleton(this);
		}
	}

	private static final long serialVersionUID = 2L;

	/**
	 * How much colorless mana this symbol represents. Zero for {0}, {C}, and
	 * {C} symbols; 2 for {2/C} symbols.
	 */
	public int colorless;

	/**
	 * What colors this symbol is. Empty for colorless, one color for colored
	 * symbols and {2/C} symbols, two colors for {C/C} symbols.
	 */
	public java.util.Collection<Color> colors;

	/**
	 * If this symbol is a Phyrexian symbol (translates into "pay 2 life" as a
	 * cost)
	 */
	public boolean isPhyrexian;

	/**
	 * If this symbol is in a cost, whether it can be paid only by mana from
	 * snow permanents; if it is in a mana pool, whether it came from a snow
	 * permanent.
	 */
	public boolean isSnow;

	/**
	 * Whether this symbol is an X symbol, or part of an explosion from an X
	 * symbol.
	 */
	public boolean isX;

	public String name;

	public int sourceID;

	public ManaSymbol(ManaType type)
	{
		this("");
		if(type == ManaType.COLORLESS)
		{
			this.colorless = 1;
			this.name = "1";
		}
		else
		{
			this.colors.add(type.color);
			this.name = type.color.getLetter();
		}
	}

	public ManaSymbol(Color color)
	{
		this(color.getLetter());
		this.colors.add(color);
	}

	/** Constructs a zero-mana symbol. */
	public ManaSymbol(String name)
	{
		this.name = name;
		this.colors = java.util.EnumSet.noneOf(Color.class);
		this.colorless = 0;
		this.isSnow = false;
		this.isX = false;
		this.sourceID = -1;
	}

	@Override
	public int compareTo(ManaSymbol o)
	{
		int difference = o.manaSymbolValue() - this.manaSymbolValue();
		return (0 == difference ? this.WTF().compareTo(o.WTF()) : difference);
	}

	/** Creates a new mana symbol that is identical to this one. */
	public ManaSymbol create()
	{
		ManaSymbol newMe = org.rnd.util.Constructor.construct(this.getClass(), (new Class[] {String.class}), (new Object[] {this.name}));

		if(newMe == null)
			return null;

		newMe.colors.addAll(this.colors);
		newMe.colorless = this.colorless;
		newMe.isPhyrexian = this.isPhyrexian;
		newMe.isSnow = this.isSnow;
		newMe.isX = this.isX;
		newMe.sourceID = this.sourceID;

		return newMe;
	}

	/**
	 * This should only be called on ManaSymbols guaranteed to have only one
	 * type (like those in a players mana pool).
	 * 
	 * @return A single ManaType which applies to this ManaSymbol.
	 */
	public ManaType getType()
	{
		for(Color color: this.colors)
			return color.getManaType();
		if(this.colorless > 0)
			return ManaType.COLORLESS;
		return null;
	}

	/** @return Whether this symbol is the specified color. */
	public boolean isColor(Color color)
	{
		return this.colors.contains(color);
	}

	/**
	 * @return True if there are exactly zero or exactly one explosions of this
	 * symbol.
	 */
	public boolean isSingular()
	{
		// if it has more than one colorless, more than one color, or one of
		// each, its not singular
		return ((this.colors.size() + this.colorless + (this.isPhyrexian ? 1 : 0)) == 1);
	}

	/** @return Whether this symbol represent zero mana. */
	public boolean isZero()
	{
		return this.colors.size() == 0 && this.colorless == 0;
	}

	private int manaSymbolValue()
	{
		int ret = 0;

		if(this.colors.contains(Color.WHITE))
			ret += 16;
		if(this.colors.contains(Color.BLUE))
			ret += 8;
		if(this.colors.contains(Color.BLACK))
			ret += 4;
		if(this.colors.contains(Color.RED))
			ret += 2;
		if(this.colors.contains(Color.GREEN))
			ret += 1;

		return ret;
	}

	/**
	 * @param state The game-state to use as the context for the evaluation
	 * @param cost The symbol to compare this against
	 * @return Whether this symbol can pay the specified part of a mana cost.
	 */
	public boolean pays(GameState state, ManaSymbol cost)
	{
		if(!this.isSingular())
			throw new UnsupportedOperationException("Non-singular ManaSymbol instances cannot pay for costs -- " + this + " is trying to pay " + cost);
		if(!cost.isSingular())
			throw new UnsupportedOperationException("ManaSymbol instances cannot pay for non-singular costs -- " + this + " is trying to pay " + cost);

		if(cost.isSnow && !this.isSnow)
			return false;
		if(cost.sourceID != -1)
		{
			Identified source = state.get(cost.sourceID);
			if(source.isGameObject())
			{
				if(((GameObject)source).manaCostRestrictions != null && !((GameObject)source).manaCostRestrictions.contains(this))
					return false;
			}
		}
		if(cost.colors.isEmpty())
			return true;
		return this.colors.containsAll(cost.colors);
	}

	/** @return A string representation of this symbol. */
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder("(");
		if(this.isX && !this.isSingular())
			ret.append("X");
		else if(((0 == this.colors.size()) && !this.isPhyrexian) || (0 < this.colorless))
			ret.append(this.colorless);
		for(Color color: this.colors)
			ret.append(color.getLetter());
		if(this.isPhyrexian)
			ret.append("P");
		ret.append(")");
		return ret.toString();
	}

	// TODO: rename this or get rid of it
	private String WTF()
	{
		return super.toString();
	}

	@Override
	public java.util.Set<Color> getColors()
	{
		return new java.util.HashSet<Color>(this.colors);
	}

	@Override
	public java.util.Set<ManaType> getManaTypes()
	{
		java.util.Set<ManaType> ret = new java.util.HashSet<ManaType>();
		for(Color c: this.colors)
			ret.add(c.getManaType());
		if(this.colorless > 0)
			ret.add(ManaType.COLORLESS);
		return ret;
	}
}
