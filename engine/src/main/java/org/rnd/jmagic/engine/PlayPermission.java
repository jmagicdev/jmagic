package org.rnd.jmagic.engine;

/**
 * Organize permissions related to playing something as a land or casting
 * something as a spell. Note that {@link #setSource(Identified)} must be called
 * before adding this to any object.
 */
public class PlayPermission
{
	private EventFactory attachedEvent = null;

	private SetGenerator forcedAlternateCost = null;

	private SetGenerator permission;

	private int sourceID = -1;

	public PlayPermission(SetGenerator permission)
	{
		this.permission = permission;
	}

	/**
	 * Attach an {@link EventFactory} which must be performed in order to use
	 * this permission.
	 */
	public void attachEvent(EventFactory event)
	{
		this.attachedEvent = event;
	}

	/**
	 * This method is intended to be called from implementors of
	 * {@link PlayableAsLand} (and whatever interface casting a spell will use).
	 * 
	 * @param state What {@link GameState} to evaluate this permission in
	 * @param whoFor The {@link Player} instance this permission is being
	 * evaluated for
	 * @return True if the evaluated permission contains {@code whoFor}. False
	 * otherwise.
	 */
	boolean evaluate(GameState state, Player whoFor)
	{
		return this.permission.evaluate(state, state.get(this.sourceID)).contains(whoFor);
	}

	/**
	 * Force an alternate cost to be paid rather than the mana cost when casting
	 * as a spell. This should be as if it were the
	 * {@link EventType.Parameter#ALTERNATE_COST} parameter to
	 * {@link EventType#CAST_SPELL_OR_ACTIVATE_ABILITY} (basically, as a union
	 * of a {@link ManaPool} and associated {@link EventFactory} instances).
	 */
	public void forceAlternateCost(SetGenerator cost)
	{
		this.forcedAlternateCost = cost;
	}

	/**
	 * @return An {@link EventFactory} that must be performed in order to use
	 * this permission, or {@code null} if there is no attached event.
	 */
	EventFactory getAttachedEvent()
	{
		return this.attachedEvent;
	}

	/**
	 * @return A {@link CostCollection} that must be the alternate cost for
	 * casting the spell, or {@code null} if there is no forced alternate cost.
	 */
	SetGenerator getForcedAlternateCost()
	{
		return this.forcedAlternateCost;
	}

	int getSourceID()
	{
		return this.sourceID;
	}

	/**
	 * The source of the effect adding a permission must be set so the
	 * {@link SetGenerator} permission evaluates properly.
	 */
	void setSource(Identified source)
	{
		this.sourceID = source.ID;
	}
}
