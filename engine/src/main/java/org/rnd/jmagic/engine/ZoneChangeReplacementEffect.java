package org.rnd.jmagic.engine;

/**
 * Represents a replacement effect that applies to zone changes. Extend this
 * class and override match and replace in order to create prevention effects
 * and other damage replacement effects.
 * 
 * When extending this class, be careful that your effect is modifiable by
 * text-change effects. This is accomplished by using a SetGenerator whenever an
 * enum is referred to (color, for example). If this is clear as mud, look at
 * Luminesce. Rather than just checking the damage sources for RED and BLACK, it
 * stores a SetGenerator (really just a Identity) containing those colors, then
 * evaluates the generator when actually doing the match. Since evaluating a
 * SetGenerator applies text change effects to the evaluation, if any text
 * change effects are changing Luminesce, the colors to match against will be
 * correctly updated.
 */
public class ZoneChangeReplacementEffect extends ReplacementEffect
{
	public java.util.Collection<ZoneChange> isReplacing;

	private SetGenerator newDestination;
	private Integer newDestinationIndex;
	private java.util.List<EventFactory> effects;

	/**
	 * Effects that happen distinctly after the zone change, with a distinct
	 * game state in between.
	 */
	public java.util.List<EventFactory> afterEffects;

	private java.util.Collection<ZoneChangePattern> patterns;
	private final boolean removeReplacedZoneChanges;

	/**
	 * Constructs a replacement effect that applies to no damage and does
	 * nothing when applied.
	 * 
	 * @param game The game in which the effect is to exist.
	 * @param name The text of the effect.
	 */
	public ZoneChangeReplacementEffect(Game game, String name)
	{
		this(game, name, false);
	}

	public ZoneChangeReplacementEffect(Game game, String name, boolean removeReplacedZoneChanges)
	{
		super(game, name);
		this.newDestination = null;
		this.newDestinationIndex = null;
		this.effects = new java.util.LinkedList<EventFactory>();
		this.afterEffects = new java.util.LinkedList<EventFactory>();
		this.isReplacing = new java.util.LinkedList<ZoneChange>();
		this.patterns = new java.util.LinkedList<ZoneChangePattern>();
		this.removeReplacedZoneChanges = removeReplacedZoneChanges;
	}

	/**
	 * Adds an event to be performed as part of this replacement effect. Events
	 * passed to this method will be performed in the order given; that is,
	 * calling replacement.addEffect(a); replacement.addEffect(b) will cause a
	 * and b to be performed in that order. Further, those events will all be
	 * performed before the movement actually happens. (See Event's methods for
	 * details)
	 */
	public void addEffect(EventFactory effect)
	{
		this.effects.add(effect);
	}

	public void addPattern(ZoneChangePattern pattern)
	{
		this.patterns.add(pattern);
	}

	/**
	 * Tells this replacement effect that it should change where an object goes
	 * when it moves. If the new destination is a library, use
	 * {@link ZoneChangeReplacementEffect#changeDestination(SetGenerator, int)}
	 * instead.
	 */
	public void changeDestination(SetGenerator newDestination)
	{
		this.newDestination = newDestination;
	}

	/**
	 * Tells this replacement effect that it should change where an object goes
	 * when it moves. If the new destination isn't a library, use
	 * {@link ZoneChangeReplacementEffect#changeDestination(SetGenerator)}
	 * instead.
	 */
	public void changeDestination(SetGenerator newDestination, int index)
	{
		this.newDestination = newDestination;
		this.newDestinationIndex = index;
	}

	/**
	 * Named for the card "Clone", which rule 616.1b was added to for.
	 * 
	 * @return False. Effects that cause an object to enter the battlefield as a
	 * copy of another object should override this method to return true.
	 */
	public boolean isCloneEffect()
	{
		return false;
	}

	/**
	 * Named for the card "Gather Specimens", which rule 616.1b was created for.
	 * 
	 * 616.1b If any of the replacement and/or prevention effects would modify
	 * under whose control an object would enter the battlefield, one of them
	 * must be chosen.
	 * 
	 * @return False. Effects that modify under whose control an object would
	 * enter the battlefield should override this method to return true.
	 */
	public boolean isGatherSpecimensEffect()
	{
		return false;
	}

	/**
	 * Applies this replacement effect to the specified zone changes. If the
	 * effect is optional, this involves asking the appropriate player whether
	 * to perform the replacement.
	 * 
	 * @param zoneChanges IN: the zone changes to replace; OUT: the replaced
	 * zone changes (possibly empty if the zone changes are prevented).
	 * @return Other events to perform as a result of replacing the zone
	 * changes.
	 */
	public final java.util.Collection<EventFactory> apply(java.util.Collection<ZoneChange> zoneChanges)
	{
		this.isReplacing = new java.util.LinkedList<ZoneChange>(zoneChanges);

		java.util.Collection<Integer> replacingEntersTheBattlefield = new java.util.LinkedList<Integer>();
		for(ZoneChange change: zoneChanges)
		{
			change.replacedBy.add(this);
			if(change.destinationZoneID == this.game.actualState.battlefield().ID)
				replacingEntersTheBattlefield.add(change.oldObjectID);
		}

		java.util.Collection<EventFactory> ret = null;
		if(this.apply(this.game.actualState))
			ret = this.replace(zoneChanges, replacingEntersTheBattlefield);
		else
			ret = java.util.Collections.emptyList();

		return ret;
	}

	/**
	 * Determines whether this effect should apply to any of the specified zone
	 * changes.
	 * 
	 * @param state The GameState to use for matching
	 * @param changes The ZoneChanges to check against.
	 * @return Collection of ZoneChanges this effect matches against.
	 */
	public final java.util.Collection<ZoneChange> match(GameState state, java.util.Collection<ZoneChange> changes)
	{
		java.util.Collection<ZoneChange> ret = new java.util.HashSet<ZoneChange>();
		for(ZoneChangePattern pattern: this.patterns)
			for(ZoneChange change: changes)
				if(pattern.match(change, this.getSourceObject(state), state))
					ret.add(change);
		return ret;
	}

	/**
	 * Use this effect to replace the specified zone changes. The calling method
	 * is responsible for marking that this effect replaced the batch. The
	 * default behavior is to return any EventFactory added with addEffect.
	 * 
	 * @return Other events to perform as a result of replacing the zone
	 * changes.
	 */
	protected java.util.List<EventFactory> replace(java.util.Collection<ZoneChange> zoneChanges, java.util.Collection<Integer> replacingEntersTheBattlefield)
	{
		if(this.removeReplacedZoneChanges)
			zoneChanges.clear();
		else if(this.newDestination != null)
			for(ZoneChange change: zoneChanges)
			{
				change.destinationZoneID = this.newDestination.evaluate(this.game, this.getSourceObject(this.game.actualState)).getOne(Zone.class).ID;
				if(this.newDestinationIndex != null)
					change.index = this.newDestinationIndex;
			}

		for(int i: replacingEntersTheBattlefield)
			for(EventFactory effect: this.effects)
				effect.cantMove(i);

		return this.effects;
	}

	// TODO : ... Do we care?
	@Override
	public java.util.Collection<GameObject> refersTo(GameState state)
	{
		return java.util.Collections.emptySet();
	}
}
