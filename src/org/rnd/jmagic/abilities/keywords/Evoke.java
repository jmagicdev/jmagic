package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Evoke extends Keyword
{
	public static final String EVOKE_COST = "Evoke:Mana";

	protected String evokeCost;

	public Evoke(GameState state, String manaCost)
	{
		super(state, "Evoke " + manaCost);
		this.evokeCost = manaCost;
	}

	@Override
	public Evoke create(Game game)
	{
		return new Evoke(game.physicalState, this.evokeCost);
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new EvokeCastAbility(this.state, this));
	}

	public static final class EvokeCastAbility extends StaticAbility
	{
		private Evoke parent;

		public EvokeCastAbility(GameState state, Evoke parent)
		{
			super(state, "You may cast this spell for its evoke cost.");
			this.parent = parent;

			this.canApply = NonEmpty.instance();

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SPECIAL_ACTION);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(new Evoke.EvokeAction.Factory(this.parent)));
			this.addEffectPart(part);
		}

		@Override
		public EvokeCastAbility create(Game game)
		{
			return new EvokeCastAbility(game.physicalState, this.parent);
		}
	}

	public static class EvokeAction extends CastSpellAction
	{
		private static class Factory extends SpecialActionFactory
		{
			private final Evoke parent;

			public Factory(Evoke parent)
			{
				this.parent = parent;
			}

			@Override
			public java.util.Set<PlayerAction> getActions(GameState state, GameObject source, Player actor)
			{
				if(!source.getOwner(state).equals(state.getPlayerWithPriority()))
					return java.util.Collections.<PlayerAction>emptySet();

				java.util.Set<PlayerAction> ret = new java.util.HashSet<PlayerAction>();

				// Only when you could begin casting the spell
				boolean makeAbility = false;
				for(PlayerAction action: state.playerActions)
					if(action instanceof CastSpellAction)
						if(((CastSpellAction)action).toBePlayedID == source.ID)
						{
							makeAbility = true;
							break;
						}

				if(makeAbility)
					ret.add(getAction(state, source, actor));

				return ret;
			}

			private EvokeAction getAction(GameState state, GameObject source, Player actor)
			{
				return new EvokeAction(state.game, this.parent, source, actor);
			}
		}

		private final int parentID;

		public EvokeAction(Game game, Evoke parent, GameObject cast, Player casting)
		{
			super(game, cast, casting, parent.ID);
			this.name = "Cast " + cast + " for its evoke cost";
			this.parentID = parent.ID;
		}

		@Override
		public GameObject play()
		{
			Evoke parent = this.game.actualState.get(this.parentID);
			ManaPool evokeCost = new ManaPool(parent.evokeCost);
			CostCollection evokeCostCollection = new CostCollection(EVOKE_COST, evokeCost);

			GameObject toBePlayed = this.game.actualState.get(this.toBePlayedID);
			Player casting = this.game.actualState.get(this.actorID);

			EventFactory castEventFactory = new EventFactory(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY, casting + " plays " + toBePlayed + ".");
			castEventFactory.parameters.put(EventType.Parameter.PLAYER, Identity.instance(casting));
			castEventFactory.parameters.put(EventType.Parameter.ACTION, Identity.instance(this));
			castEventFactory.parameters.put(EventType.Parameter.OBJECT, Identity.instance(toBePlayed));
			castEventFactory.parameters.put(EventType.Parameter.ALTERNATE_COST, Identity.instance(evokeCostCollection));
			Event castEvent = castEventFactory.createEvent(this.game, toBePlayed);
			if(!castEvent.perform(null, true))
				return null;

			GameObject cast = castEvent.getResult().getOne(GameObject.class);
			return cast;
		}
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.<NonStaticAbility>singletonList(new EvokeSacrificeAbility(this.state));
	}

	public static final class EvokeSacrificeAbility extends EventTriggeredAbility
	{
		public EvokeSacrificeAbility(GameState state)
		{
			super(state, "If you cast this creature for its evoke cost, it's sacrificed when it enters the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.interveningIf = WasEvoked.instance(ABILITY_SOURCE_OF_THIS);

			this.addEffect(sacrificeThis("this creature"));
		}
	}
}
