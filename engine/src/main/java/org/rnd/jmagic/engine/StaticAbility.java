package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/** Represents a static ability. */
public abstract class StaticAbility extends Identified implements Linkable, Sanitizable
{
	private Linkable.Manager linkManager;

	/** The effect this ability has on the game state. */
	public int effectID;

	/**
	 * Represents whether this ability can apply. This ability can apply if this
	 * generator evaluates to a non-empty set.
	 */
	public SetGenerator canApply;

	/** What object this ability is printed on or granted to. */
	public int sourceID;

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

	/**
	 * Constructs a static ability that does nothing and applies only when the
	 * object it's on is on the battlefield.
	 * <p>
	 * 112.6. Abilities ... usually function only while [the object they're on]
	 * is on the battlefield.
	 * <p>
	 * 113.4. Abilities of emblems function in the command zone.
	 * 
	 * @param state The game state in which this ability exists.
	 * @param abilityText The text of this ability.
	 */
	public StaticAbility(GameState state, String abilityText)
	{
		super(state);

		this.setName(abilityText);

		this.linkManager = new Linkable.Manager();

		ContinuousEffect effect = new ContinuousEffect(state, abilityText);
		effect.staticSourceID = this.ID;
		this.effectID = effect.ID;
		this.sourceID = -1;
		this.createdByTimestamp = -1;

		SetGenerator isEmblem = EmblemFilter.instance(This.instance());
		SetGenerator inCommandZone = InZone.instance(CommandZone.instance());
		SetGenerator isEmblemInCommandZone = Intersect.instance(isEmblem, inCommandZone);
		SetGenerator permanentsAndPlayers = Union.instance(Players.instance(), Permanents.instance(), VoidedObjects.instance());
		this.canApply = Union.instance(Intersect.instance(permanentsAndPlayers, This.instance()), isEmblemInCommandZone);
	}

	public void addEffectPart(ContinuousEffect.Part part)
	{
		this.getEffect().parts.add(part);
	}

	public void addEffectPart(ContinuousEffect.Part... parts)
	{
		for(ContinuousEffect.Part part: parts)
			this.addEffectPart(part);
	}

	/**
	 * Determines whether this ability can apply. By default, an ability can
	 * apply if the object it's on is a permanent. Override this method for
	 * abilities that can apply in other zones or have restrictions on them ("as
	 * long as", etc).
	 * 
	 * @return Whether this ability can apply.
	 */
	public final boolean canApply()
	{
		Identified source = this.getSource(this.game.actualState);

		// if it's on a player, it can apply
		if(source.isPlayer())
			return true;

		return !(this.canApply.evaluate(this.game, source).isEmpty());
	}

	/** Java-copies this ability. */
	@Override
	public StaticAbility clone(GameState state)
	{
		StaticAbility ret = (StaticAbility)super.clone(state);
		ret.effectID = this.effectID;
		ret.linkManager = this.linkManager.clone();
		return ret;
	}

	@Override
	public void copy()
	{
		super.copy();
		this.getEffect().copy();
	}

	public StaticAbility create(Game game)
	{
		return org.rnd.util.Constructor.construct(this.getClass(), (new Class[] {GameState.class}), (new Object[] {game.physicalState}));
	}

	public ContinuousEffect getEffect()
	{
		return this.state.get(this.effectID);
	}

	@Override
	public Manager getLinkManager()
	{
		return this.linkManager;
	}

	/**
	 * @return The version of this ability that is present in the game's
	 * physical state. Probably an ability printed on a card.
	 */
	@Override
	public StaticAbility getPhysical()
	{
		return (StaticAbility)super.getPhysical();
	}

	/**
	 * @return The object this ability is printed on or granted to, as that
	 * object exists in the given game state.
	 */
	public Identified getSource(GameState state)
	{
		return state.get(this.sourceID);
	}

	/**
	 * @return The id of the object this ability is printed on or granted to.
	 */
	public int getSourceID()
	{
		return this.sourceID;
	}

	/**
	 * @return The characteristics this ability defines, if this is a
	 * characteristic-defining ability. An empty collection, otherwise.
	 */
	public java.util.Collection<Characteristics.Characteristic> definesCharacteristics()
	{
		return java.util.Collections.emptySet();
	}

	/** @return True. */
	@Override
	public boolean isStaticAbility()
	{
		return true;
	}

	@Override
	public java.io.Serializable sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedStaticAbility(state.<StaticAbility>get(this.ID));
	}

	/**
	 * Normally, we don't want to override toString, but in this case, we don't
	 * know what the source is until after construction.
	 * 
	 * @return A string representation of this ability.
	 */
	@Override
	public String toString()
	{
		return super.toString() + " from " + this.game.actualState.get(this.sourceID);
	}
}
