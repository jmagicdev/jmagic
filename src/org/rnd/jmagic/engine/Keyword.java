package org.rnd.jmagic.engine;

/** Represents a keyword ability. */
abstract public class Keyword extends Identified implements Sanitizable
{
	private java.util.Set<Integer> abilitiesGranted;

	/**
	 * If this ability was granted by a continuous effect, the timestamp of that
	 * effect; else -1.
	 */
	public int createdByTimestamp;

	/**
	 * If this ability was granted by a continuous effect, the ID of that
	 * effect; else -1.
	 */
	public int grantedByID;

	private boolean ghost;

	/**
	 * Constructs a keyword ability that does nothing.
	 * 
	 * @param state The game state in which this keyword exists.
	 * @param name The name of this keyword ability.
	 */
	public Keyword(GameState state, String name)
	{
		super(state);

		this.setName(name);

		this.abilitiesGranted = null;
		this.createdByTimestamp = -1;
		this.grantedByID = -1;
		this.ghost = false;
	}

	public final boolean apply(GameObject source)
	{
		this.abilitiesGranted = new java.util.HashSet<Integer>();
		boolean failed = false;

		if(null != source)
		{
			for(NonStaticAbility ability: this.createNonStaticAbilities())
			{
				ability.grantedByID = this.grantedByID;
				if(source.addAbility(ability))
					this.abilitiesGranted.add(ability.ID);
				else
					failed = true;
			}
			for(StaticAbility ability: this.createStaticAbilities())
			{
				ability.createdByTimestamp = this.createdByTimestamp;
				ability.grantedByID = this.grantedByID;
				if(source.addAbility(ability))
					this.abilitiesGranted.add(ability.ID);
				else
					failed = true;
			}
			for(EventFactory effect: this.createSpellAbilities())
			{
				effect.hidden = true;
				source.addEffect(effect);
			}
		}

		this.applyHook(source);

		return !failed;
	}

	public boolean apply(Player source)
	{
		this.abilitiesGranted = new java.util.HashSet<Integer>();
		boolean failed = false;

		if(null != source)
		{
			for(StaticAbility ability: this.createStaticAbilities())
			{
				if(source.addAbility(ability))
					this.abilitiesGranted.add(ability.ID);
				else
					failed = true;
			}
		}

		return !failed;
	}

	/**
	 * A method to override when anything needs to execute code after apply
	 * 
	 * @param source Object to act on
	 * */
	public void applyHook(GameObject source)
	{
		// Intentionally left ineffectual
	}

	/** Java-copies this keyword ability. */
	@Override
	public Keyword clone(GameState state)
	{
		Keyword ret = (Keyword)super.clone(state);
		if(null != this.abilitiesGranted)
			ret.abilitiesGranted = new java.util.HashSet<Integer>(this.abilitiesGranted);
		// this.ghost copied by super.clone()
		return ret;
	}

	public Keyword create(Game game)
	{
		return org.rnd.util.Constructor.construct(this.getClass(), new Class[] {GameState.class}, new Object[] {game.physicalState});
	}

	/**
	 * Creates a list of activated and triggered abilities (by default, empty)
	 * that this keyword represents.
	 * 
	 * @return By default, an empty list. Individual keywords will override this
	 * method to return the non-static abilities represented by that keyword.
	 */
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.emptyList();
	}

	/**
	 * Creates a list of {@link EventFactory} (by default empty) that this
	 * keyword represents.
	 * 
	 * NOTE: Any keyword using this method will not properly remove these spell
	 * abilities when the keyword is removed.
	 * 
	 * @return By default, an empty list. Individual keywords will override this
	 * method to return the spell abilities represented by that keyword.
	 */
	protected java.util.List<EventFactory> createSpellAbilities()
	{
		return java.util.Collections.emptyList();
	}

	/**
	 * Creates a list of static abilities (by default, empty) that this keyword
	 * represents.
	 * 
	 * @return By default, an empty list. Individual keywords will override this
	 * method to return the static abilities represented by that keyword.
	 */
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.emptyList();
	}

	public Set getAbilitiesGranted()
	{
		if(this.abilitiesGranted == null)
			return null;
		Set ret = new Set();
		for(Integer abilityID: this.abilitiesGranted)
			ret.add(this.state.get(abilityID));
		return ret;
	}

	public void ghost()
	{
		this.ghost = true;

		Keyword actual = this.game.actualState.get(this.ID);
		if(!actual.ghost)
		{
			actual.ghost();
			return;
		}

		if(this.state != this.game.physicalState)
			actual.clone(this.game.physicalState);
	}

	/**
	 * @return Whether this is the "enchant" keyword. Needed for various rules
	 * relating to Auras.
	 */
	public boolean isEnchant()
	{
		return false;
	}

	/**
	 * @return Whether this is the "haste" keyword. Needed for the summoning
	 * sickness rule.
	 */
	public boolean isHaste()
	{
		return false;
	}

	@Override
	public boolean isKeyword()
	{
		return true;
	}

	/**
	 * @return Whether this is a [LEVEL> symbol. Purely for the benefit of
	 * SanitizedGameObject.
	 */
	public boolean isLevelSymbol()
	{
		return false;
	}

	/**
	 * @return Whether this is the "morph" keyword. Needed to determine whether
	 * to generate a turn-face-up action for a particular object.
	 */
	public boolean isMorph()
	{
		return false;
	}

	/**
	 * @return Whether this is the "splice" keyword. Needed to determine which
	 * cards can be spliced onto a spell as it's being cast.
	 */
	public boolean isSplice()
	{
		return false;
	}

	/**
	 * @return Whether this is the "suspend" keyword. Needed for the definition
	 * of "suspended card".
	 */
	public boolean isSuspend()
	{
		return false;
	}

	public final boolean remove(Player source)
	{
		if(this.abilitiesGranted == null)
			return true;

		if(null != source)
		{
			java.util.Set<Object> toRemove = new java.util.HashSet<Object>();

			for(Integer abilityID: this.abilitiesGranted)
			{
				Identified i = source.state.get(abilityID);
				if(StaticAbility.class.isAssignableFrom(i.getClass()))
				{
					StaticAbility ability = (StaticAbility)i;
					if(source.removeAbility(ability))
						toRemove.add(ability);
				}
			}

			this.abilitiesGranted.removeAll(toRemove);
		}

		return this.abilitiesGranted.isEmpty();
	}

	public final boolean removeFrom(GameObject source)
	{
		if(this.abilitiesGranted == null)
			return true;

		if(null != source)
		{
			java.util.Set<Integer> toRemove = new java.util.HashSet<Integer>();

			for(Integer abilityID: this.abilitiesGranted)
			{
				Identified i = source.state.get(abilityID);
				if(StaticAbility.class.isAssignableFrom(i.getClass()))
				{
					StaticAbility ability = (StaticAbility)i;
					if(source.removeAbility(ability))
						toRemove.add(abilityID);
				}
				else if(NonStaticAbility.class.isAssignableFrom(i.getClass()))
				{
					NonStaticAbility ability = (NonStaticAbility)i;
					if(source.removeAbility(ability))
						toRemove.add(abilityID);
				}
			}

			this.abilitiesGranted.removeAll(toRemove);
		}

		this.removeHook(source);

		return this.abilitiesGranted.isEmpty();
	}

	/**
	 * A method to override when anything needs to execute code after apply
	 * 
	 * @param source Object to act on
	 * */
	public void removeHook(GameObject source)
	{
		// Intentionally left ineffectual
	}

	@Override
	public java.io.Serializable sanitize(org.rnd.jmagic.engine.GameState state, org.rnd.jmagic.engine.Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedIdentified(this);
	}

	public void setAbilitiesGranted(java.util.Set<Integer> newAbilitiesGranted)
	{
		if(this.abilitiesGranted != null)
			throw new RuntimeException("Cannot set abilitiesGranted of a keyword that already granted abilities");
		this.abilitiesGranted = new java.util.HashSet<Integer>(newAbilitiesGranted);
	}
}
