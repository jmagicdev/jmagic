package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/**
 * A class implements the {@link Castable} interface to indicate it can be cast.
 * Classes that implement this must implement the following defaults:
 * <ul>
 * <li>An object that's both a land and another card type (for example, an
 * artifact land) can only be played as a land. It can't be cast as a spell.</li>
 * <li>A player who has priority may cast an artifact card from his or her hand
 * during a main phase of his or her turn when the stack is empty.</li>
 * <li>A player who has priority may cast a creature card from his or her hand
 * during a main phase of his or her turn when the stack is empty.</li>
 * <li>A player who has priority may cast an enchantment card from his or her
 * hand during a main phase of his or her turn when the stack is empty.</li>
 * <li>A player who has priority may cast an instant card from his or her hand.</li>
 * <li>A player who has priority may cast a planeswalker card from his or her
 * hand during a main phase of his or her turn when the stack is empty.</li>
 * <li>A player who has priority may cast a sorcery card from his or her hand
 * during a main phase of his or her turn when the stack is empty.</li>
 * <li>Each tribal card has another card type. Casting and resolving a tribal
 * card follows the rules for casting and resolving a card of the other card
 * type.</li>
 * </ul>
 * See {@link Castable.Simple} the implementation you must delegate to.
 */
public interface Castable
{
	/**
	 * You must delegate to this class for the implementation of
	 * {@link Castable}. Note the special behavior for calling
	 * {@link #getCastActions(GameState, Player)}.
	 */
	class Simple implements Castable, Cloneable
	{
		private java.util.List<PlayPermission> locations;

		private java.util.List<PlayPermission> timings;

		Simple(Identified source)
		{
			SetGenerator zoneOfThis = ZoneContaining.instance(This.instance());
			SetGenerator hands = HandOf.instance(Players.instance());
			SetGenerator thisHand = Intersect.instance(zoneOfThis, hands);

			PlayPermission locationPermission = new PlayPermission(OwnerOf.instance(thisHand));
			locationPermission.setSource(source);

			this.locations = new java.util.LinkedList<PlayPermission>();
			this.locations.add(locationPermission);

			SetGenerator isInstant = Intersect.instance(HasType.instance(Type.INSTANT), This.instance());
			SetGenerator timing = IfThenElse.instance(isInstant, PlayerWithPriority.instance(), PlayerCanPlaySorcerySpeed.instance());

			PlayPermission timingPermission = new PlayPermission(timing);
			timingPermission.setSource(source);

			this.timings = new java.util.LinkedList<PlayPermission>();
			this.timings.add(timingPermission);
		}

		@Override
		public void addCastablePermissionLocation(PlayPermission permission)
		{
			this.locations.add(permission);
		}

		@Override
		public void addCastablePermissionTiming(PlayPermission permission)
		{
			this.timings.add(permission);
		}

		@Override
		protected Simple clone()
		{
			try
			{
				Simple ret = (Simple)super.clone();
				ret.locations = new java.util.LinkedList<PlayPermission>(this.locations);
				ret.timings = new java.util.LinkedList<PlayPermission>(this.timings);
				return ret;
			}
			catch(CloneNotSupportedException e)
			{
				throw new RuntimeException();
			}
		}

		/**
		 * This class needs some information from the object delegating to it,
		 * so calling this method will throw a
		 * {@link UnsupportedOperationException}. Instead, call the
		 * {@link #getCastActions(GameState, Player, GameObject)} version.
		 */
		@Deprecated
		@Override
		public java.util.List<CastSpellAction> getCastActions(GameState state, Player who)
		{
			throw new UnsupportedOperationException();
		}

		public java.util.List<CastSpellAction> getCastActions(GameState state, Player who, GameObject spell)
		{
			// It cannot be a land
			if(spell.getTypes().contains(Type.LAND))
				return java.util.Collections.emptyList();

			// Can't cast ghosts
			if(spell.isGhost())
				return java.util.Collections.emptyList();

			java.util.List<CastSpellAction> ret = null;
			for(PlayPermission l: this.locations)
			{
				if(l.evaluate(state, who))
				{
					for(PlayPermission t: this.timings)
					{
						if(t.evaluate(state, who))
						{
							CastSpellAction action = new CastSpellAction(state.game, spell, who, 0);

							SetGenerator costLocation = l.getForcedAlternateCost();
							SetGenerator costTiming = t.getForcedAlternateCost();
							if(null != costLocation)
							{
								if(null != costTiming)
								{
									SetGenerator empty = Empty.instance();
									// Handle the special case where both forced
									// alternate costs are empty
									if((empty == costLocation) && (empty == costTiming))
										action.forceAlternateCost(empty);
									// Otherwise, both forced alternate costs
									// can't be paid at the same time, so skip
									// this combination
									else
										continue;
								}
								else
									action.forceAlternateCost(costLocation);
							}
							else if(null != costTiming)
								action.forceAlternateCost(costTiming);

							EventFactory eventLocation = l.getAttachedEvent();
							if(null != eventLocation)
								action.attachEvent(eventLocation, l.getSourceID());

							EventFactory eventTiming = t.getAttachedEvent();
							if(null != eventTiming)
								action.attachEvent(eventTiming, t.getSourceID());

							// 601.5. A player can't begin to cast a spell
							// that's prohibited from being cast.
							if(action.attempt())
							{
								if(null == ret)
									ret = new java.util.LinkedList<CastSpellAction>();
								ret.add(action);
							}
						}
					}
				}
			}

			if(null == ret)
				return java.util.Collections.emptyList();
			return ret;
		}
	}

	void addCastablePermissionLocation(PlayPermission permission);

	void addCastablePermissionTiming(PlayPermission permission);

	/**
	 * @param state What {@link GameState} to evaluate permissions in
	 * @param who The {@link Player} instance permissions should be evaluated
	 * for
	 * @return True if this is castable by {@code who}. False otherwise.
	 */
	java.util.List<CastSpellAction> getCastActions(GameState state, Player who);
}
