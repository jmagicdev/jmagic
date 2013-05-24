package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/**
 * A class implements the {@link PlayableAsLand} interface to indicate it can be
 * played as a land. Classes that implement this must implement the following
 * defaults:
 * <ul>
 * <li>A player who has priority may play a land card from his or her hand
 * during a main phase of his or her turn when the stack is empty.</li>
 * <li>A player can't play a land, for any reason, if it isn't his or her turn.</li>
 * </ul>
 * See {@link PlayableAsLand.Simple} the implementation you must delegate to.
 */
interface PlayableAsLand
{
	/**
	 * You must delegate to this class for the implementation of
	 * {@link PlayableAsLand}. Note the special behavior for calling
	 * {@link #isPlayableAsLandBy(GameState, Player)}.
	 */
	class Simple implements PlayableAsLand, Cloneable
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

			PlayPermission timingPermission = new PlayPermission(PlayerCanPlaySorcerySpeed.instance());
			timingPermission.setSource(source);

			this.timings = new java.util.LinkedList<PlayPermission>();
			this.timings.add(timingPermission);
		}

		@Override
		public void addPlayableAsLandPermissionLocation(PlayPermission permission)
		{
			this.locations.add(permission);
		}

		@Override
		public void addPlayableAsLandPermissionTiming(PlayPermission permission)
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
		 * {@link #isPlayableAsLandBy(GameState, Player, GameObject)} version.
		 */
		@Deprecated
		@Override
		public boolean isPlayableAsLandBy(GameState state, Player who)
		{
			throw new UnsupportedOperationException();
		}

		public boolean isPlayableAsLandBy(GameState state, Player who, GameObject land)
		{
			// It has to be a land
			if(!(land.getTypes().contains(Type.LAND)))
				return false;

			// Can't play ghosts as a land
			if(land.isGhost())
				return false;

			// If it's not the player's turn, the player can't play lands
			if(state.currentTurn().ownerID != who.ID)
				return false;

			for(PlayPermission l: this.locations)
				if(l.evaluate(state, who))
					for(PlayPermission t: this.timings)
						if(t.evaluate(state, who))
							return true;
			return false;
		}
	}

	void addPlayableAsLandPermissionLocation(PlayPermission permission);

	void addPlayableAsLandPermissionTiming(PlayPermission permission);

	/**
	 * @param state What {@link GameState} to evaluate permissions in
	 * @param who The {@link Player} instance permissions should be evaluated
	 * for
	 * @return True if this is playable as a land by {@code who}. False
	 * otherwise.
	 */
	boolean isPlayableAsLandBy(GameState state, Player who);
}
