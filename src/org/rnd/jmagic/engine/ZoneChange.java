package org.rnd.jmagic.engine;

/**
 * A movement of a single GameObject from one Zone to another. When adding a
 * field to this class, you will almost certainly need to add a line of code to
 * MOVE_OBJECTS.perform that sets that field to an appropriate value.
 */
public class ZoneChange implements Sanitizable
{
	/**
	 * Represents the replacement of a single ZoneChange by a single
	 * ZoneChangeReplacementEffect.
	 */
	public static class Replacement implements Sanitizable
	{
		public ZoneChangeReplacementEffect effect;
		public ZoneChange zoneChange;

		public Replacement(ZoneChangeReplacementEffect effect, ZoneChange zoneChange)
		{
			this.effect = effect;
			this.zoneChange = zoneChange;
		}

		@Override
		public java.io.Serializable sanitize(GameState state, Player whoFor)
		{
			return new org.rnd.jmagic.sanitized.SanitizedZoneChange.Replacement(this.effect, this.zoneChange, state, whoFor);
		}
	}

	/**
	 * The ID of the GameObject (or the Game if the ID is 0) that caused this
	 * zone change.
	 */
	public int causeID = -1;

	/**
	 * The ID of the Player who will control the new GameObject when it is
	 * inserted into the new Zone. This must be specified if the destination
	 * Zone is either the Battlefield or the Stack and is unused otherwise.
	 */
	public int controllerID = -1;

	/**
	 * The ID of the Zone the new GameObject will be inserted into.
	 */
	public int destinationZoneID = -1;

	/**
	 * Events to be created and performed before replacement effects are checked
	 * (ex: tapping the land for Rampant Growth)
	 */
	public java.util.Collection<EventFactory> events = new java.util.LinkedList<EventFactory>();

	/**
	 * If the new object should have the face down status, a class defining
	 * characteristics for that object to assume while it's face down. The class
	 * must have a (GameObject target) constructor.
	 */
	public Class<? extends Characteristics> faceDownCharacteristics = null;

	/**
	 * Whether the new object should be hidden to all players.
	 */
	public boolean hidden = false;

	/**
	 * Where in the new Zone it will be inserted. Positive numbers count from
	 * the "top" of the zone (1 = top, 2 = second from top, 3 = Enigma
	 * Sphinx...), negative numbers count from the "bottom" of the zone (-1 =
	 * bottom, -2 = second from bottom...)
	 */
	public int index = 1;

	/**
	 * Whether this zone-change is to pay a cost.
	 */
	public boolean isCost = false;

	public boolean isEffect = true;

	/**
	 * Whether this zone-change is caused by a discard.
	 */
	public boolean isDiscard = false;

	/**
	 * Whether this zone-change is caused by a draw.
	 */
	public boolean isDraw = false;

	/**
	 * Whether this zone-change is the last step of a spell resolving.
	 */
	public boolean isSpellResolution = false;

	/**
	 * The ID of the new GameObject to be inserted into the destination Zone.
	 * Only Event will set this.
	 */
	public int newObjectID = -1;

	/**
	 * The ID of the old GameObject to be removed from the source Zone.
	 */
	public int oldObjectID = -1;

	/**
	 * If true, order the object moved by this zone change randomly with respect
	 * to other objects being moved to the same zone and index as this object.
	 * If false, and the destination zone is an ordered zone, ask the object's
	 * owner to order those objects.
	 */
	public boolean random;

	/**
	 * The replacement effects that replaced this zone change, if any.
	 */
	public java.util.Collection<ZoneChangeReplacementEffect> replacedBy = new java.util.LinkedList<ZoneChangeReplacementEffect>();

	/**
	 * The ID of the Zone the old GameObject will be removed from.
	 */
	public int sourceZoneID = -1;

	public GameObject getCause(GameState state)
	{
		if(this.causeID == 0)
			return null;
		return state.get(this.causeID);
	}

	@Override
	public org.rnd.jmagic.sanitized.SanitizedZoneChange sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedZoneChange(this);
	}
}
