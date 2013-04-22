package org.rnd.jmagic.engine;

/** Represents an ability that can resolve. */
public abstract class NonStaticAbility extends GameObject implements Linkable
{
	/**
	 * If this ability was granted to an object by an effect, the ID of the
	 * object that effect came from. Otherwise, -1.
	 */
	public int grantedByID;

	protected Linkable.Manager linkManager;

	private ManaPool manaAdded;

	/** The ability that triggered or was activated to create this */
	protected int printedVersionID;

	/** What object this ability is printed on or granted to. */
	public int sourceID;

	/** Constructs an ability that does nothing. */
	NonStaticAbility(GameState state, String abilityText)
	{
		super(state);

		this.setName(abilityText);

		this.grantedByID = -1;
		this.linkManager = new Linkable.Manager();
		this.manaAdded = null;
		this.printedVersionID = -1;
		this.sourceID = -1;
	}

	public void addedMana(java.util.Collection<ManaSymbol> symbols)
	{
		if(this.manaAdded == null)
			this.manaAdded = new ManaPool(symbols);
		else
			this.manaAdded.addAll(symbols);
	}

	protected boolean addsMana()
	{
		for(Mode mode: this.getModes())
			for(EventFactory e: mode.effects)
				if(e.type.addsMana())
					return true;

		return false;
	}

	@Override
	public NonStaticAbility clone(org.rnd.jmagic.engine.GameState state)
	{
		NonStaticAbility ret = (NonStaticAbility)super.clone(state);
		ret.linkManager = this.linkManager.clone();
		return ret;
	}

	@Override
	public NonStaticAbility counterThisObject(Set counterer)
	{
		this.game.physicalState.stack().remove(this);
		return this;
	}

	@Override
	public NonStaticAbility counterThisObject(Set counterer, Zone counterTo)
	{
		this.game.physicalState.stack().remove(this);
		return this;
	}

	@Override
	public NonStaticAbility createToMove(Zone destination)
	{
		NonStaticAbility ret = (NonStaticAbility)super.createToMove(destination);
		ret.sourceID = this.sourceID;
		return ret;
	}

	@Override
	public Characteristics getCharacteristics()
	{
		Characteristics ret = super.getCharacteristics();
		ret.sourceID = this.sourceID;
		return ret;
	}

	/** @return Zero. */
	@Override
	public int getConvertedManaCost()
	{
		return 0;
	}

	@Override
	public Manager getLinkManager()
	{
		return this.linkManager;
	}

	public ManaPool getManaAdded()
	{
		return this.manaAdded;
	}

	@Override
	public NonStaticAbility getPhysical()
	{
		return (NonStaticAbility)super.getPhysical();
	}

	public Identified getSource(GameState state)
	{
		return state.get(this.sourceID);
	}

	public int getSourceID()
	{
		return this.sourceID;
	}

	public boolean hasTargets()
	{
		for(Mode mode: this.getModes())
			if(!mode.targets.isEmpty())
				return true;
		return false;
	}

	/** Resolves this ability. */
	@Override
	public void resolve()
	{
		this.followInstructions();
		// 608.2k ... As the final part of an ability's resolution, the ability
		// is removed from the stack and ceases to exist.
		this.game.physicalState.stack().remove(this);

		if(this.game.physicalState.currentlyResolvingManaAbilities.remove(this))
			this.getPhysical().ghost();
	}

	@Override
	public org.rnd.jmagic.sanitized.SanitizedNonStaticAbility sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedNonStaticAbility(state.<NonStaticAbility>get(this.ID), whoFor);
	}

	@Override
	public void setCharacteristics(Characteristics characteristics)
	{
		super.setCharacteristics(characteristics);
		this.sourceID = characteristics.sourceID;
	}

	/**
	 * If this ability is on the stack, gets the ability that triggered or was
	 * activated to produce this ability. Otherwise, get this ability in the
	 * given state.
	 */
	public NonStaticAbility getPrintedVersion(GameState state)
	{
		if(this.printedVersionID == -1)
			return state.get(this.ID);
		return state.get(this.printedVersionID);
	}
}
