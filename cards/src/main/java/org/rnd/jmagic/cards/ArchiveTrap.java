package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archive Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class ArchiveTrap extends Card
{
	public static final class SearchedTheirLibraryThisTurn extends SetGenerator
	{
		private static class SearchTracker extends Tracker<java.util.Set<Integer>>
		{
			private java.util.HashSet<Integer> who = new java.util.HashSet<Integer>();
			private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.who);

			@SuppressWarnings("unchecked")
			@Override
			public SearchTracker clone()
			{
				SearchTracker ret = (SearchTracker)super.clone();
				ret.who = (java.util.HashSet<Integer>)this.who.clone();
				ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.who);
				return ret;
			}

			@Override
			protected java.util.Set<Integer> getValueInternal()
			{
				return this.unmodifiable;
			}

			@Override
			protected boolean match(GameState state, Event event)
			{
				if(event.type != EventType.SEARCH)
					return false;

				Set searched = event.parametersNow.get(EventType.Parameter.CARD).evaluate(state, null);
				Player who = event.parametersNow.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class);

				for(Zone zone: searched.getAll(Zone.class))
				{
					if(!zone.isLibrary())
						continue;

					if(zone.getOwner(state).equals(who))
						return true;
				}

				return false;
			}

			@Override
			protected void reset()
			{
				this.who.clear();
			}

			@Override
			protected void update(GameState state, Event event)
			{
				Player who = event.parametersNow.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class);
				this.who.add(who.ID);
			}
		}

		private static SetGenerator _instance = new SearchedTheirLibraryThisTurn();

		private SearchedTheirLibraryThisTurn()
		{
			// singleton generator
		}

		public static SetGenerator instance()
		{
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			java.util.Set<Integer> playerIDs = state.getTracker(SearchTracker.class).getValue(state);
			for(int playerID: playerIDs)
				ret.add(state.get(playerID));
			return ret;
		}
	}

	public ArchiveTrap(GameState state)
	{
		super(state);

		// If an opponent searched his or her library this turn, you may pay (0)
		// rather than pay Archive Trap's mana cost.
		state.ensureTracker(new SearchedTheirLibraryThisTurn.SearchTracker());
		SetGenerator trapCondition = Intersect.instance(SearchedTheirLibraryThisTurn.instance(), OpponentsOf.instance(You.instance()));
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If an opponent searched his or her library this turn", "(0)"));

		// Target opponent puts the top thirteen cards of his or her library
		// into his or her graveyard.
		Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");
		this.addEffect(millCards(targetedBy(target), 13, "Target opponent puts the top thirteen cards of his or her library into his or her graveyard."));
	}
}
