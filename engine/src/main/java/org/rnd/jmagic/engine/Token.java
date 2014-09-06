package org.rnd.jmagic.engine;

/** Represents a token. */
public final class Token extends GameObject
{
	private Set abilityClasses;

	private boolean wasOnTheBattlefield;

	/**
	 * Creates a blank token.
	 *
	 * @param state The gamestate in which the token exists.
	 * @param abilityClasses Any abilities the token is intended to have at all
	 * times
	 * @param name The name to give the token.
	 */
	public Token(GameState state, Set abilityClasses, String name)
	{
		super(state);

		this.abilityClasses = abilityClasses;
		this.wasOnTheBattlefield = false;

		Class<?>[] constructorTypes = new Class<?>[] {GameState.class};
		Object[] constructorArguments = new Object[] {state};

		if(null != abilityClasses)
		{
			for(Class<? extends Keyword> a: abilityClasses.getAllClasses(Keyword.class))
				this.addAbility(org.rnd.util.Constructor.construct(a, constructorTypes, constructorArguments));
			for(Class<? extends NonStaticAbility> a: abilityClasses.getAllClasses(NonStaticAbility.class))
				this.addAbility(org.rnd.util.Constructor.construct(a, constructorTypes, constructorArguments));
			for(Class<? extends StaticAbility> a: abilityClasses.getAllClasses(StaticAbility.class))
				this.addAbility(org.rnd.util.Constructor.construct(a, constructorTypes, constructorArguments));
		}

		this.setName(name);
	}

	@Override
	public Token create(Game game)
	{
		Token ret = new Token(game.physicalState, this.abilityClasses, this.getName());

		ret.addSuperTypes(this.getSuperTypes());
		ret.setPower(this.getPower());
		ret.setToughness(this.getToughness());
		ret.getColors().addAll(this.getColors());
		ret.addSubTypes(this.getSubTypes());
		ret.addTypes(this.getTypes());

		return ret;
	}

	@Override
	public Token createToMove(Zone destination)
	{
		Token ret = (Token)super.createToMove(destination);
		ret.wasOnTheBattlefield = this.wasOnTheBattlefield;
		return ret;
	}

	/**
	 * @return The converted mana cost of this token. Probably zero.
	 */
	@Override
	public int[] getConvertedManaCost()
	{
		return java.util.Arrays.stream(this.getManaCost()).mapToInt(t -> null == t ? 0 : t.converted()).toArray();
	}

	/**
	 * @return Whether this token is a permanent. Any time this method returns
	 * false, the token should cease to exist the next time state-based actions
	 * are checked.
	 */
	@Override
	public boolean isPermanent()
	{
		return (!this.isGhost() && this.zoneID == this.game.actualState.battlefield().ID);
	}

	/** @return True. */
	@Override
	public boolean isToken()
	{
		return true;
	}

	/**
	 * Tokens can't resolve, since if one did somehow get to the stack,
	 * state-based actions should obliterate it long before the engine tries to
	 * resolve anything on the stack.
	 */
	@Override
	public final void resolve()
	{
		throw new IllegalStateException("Tried to resolve a token...");
	}

	@Override
	void setManaCost(ManaPool manaCost)
	{
		super.setManaCost(manaCost);
		this.setColors();
	}

	/**
	 * Tells this token it's in a different zone. If this token is currently in
	 * play, then subsequent calls to wasInPlay() will return true.
	 *
	 * @param zone The new zone.
	 */
	@Override
	public void setZone(Zone zone)
	{
		if(this.pastSelf != -1 && this.getPastSelf().zoneID == this.game.actualState.battlefield().ID)
			this.wasOnTheBattlefield = true;

		super.setZone(zone);
	}

	/**
	 * 110.5f A token that has left the battlefield can't change zones.
	 *
	 * @return Whether this token has been in play.
	 */
	public final boolean wasOnBattlefield()
	{
		return this.wasOnTheBattlefield;
	}
}
