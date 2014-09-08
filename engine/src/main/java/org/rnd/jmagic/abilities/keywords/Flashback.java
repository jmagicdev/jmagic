package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Flashback extends Keyword
{
	private static String flashbackName(CostCollection cost)
	{
		if(cost.events.isEmpty())
			return "Flashback " + cost.manaCost;

		return "Flashback\u2014" + cost.toString();
	}

	public static final String COST_TYPE = "Flashback";
	protected CostCollection flashbackCost;

	public Flashback(GameState state, String manaCost)
	{
		super(state, "Flashback " + manaCost);
		this.flashbackCost = new CostCollection(COST_TYPE, manaCost);
	}

	public Flashback(GameState state, CostCollection cost)
	{
		super(state, flashbackName(cost));
		this.flashbackCost = cost;
	}

	@Override
	public Flashback create(Game game)
	{
		return new Flashback(game.physicalState, this.flashbackCost);
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();

		ret.add(new FlashbackCastAbility(this.state, this));
		ret.add(new FlashbackExileReplacement(this.state));

		return ret;
	}

	public static final class FlashbackCastAbility extends StaticAbility
	{
		private Flashback parent;

		public FlashbackCastAbility(GameState state, Flashback parent)
		{
			super(state, "You may cast this card from your graveyard for its flashback cost. Then exile it.");
			this.parent = parent;

			this.canApply = THIS_IS_IN_A_GRAVEYARD;

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SPECIAL_ACTION);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(new Flashback.FlashbackAction.Factory(this.parent)));
			this.addEffectPart(part);
		}

		@Override
		public FlashbackCastAbility create(Game game)
		{
			return new FlashbackCastAbility(game.physicalState, this.parent);
		}
	}

	public static class FlashbackAction extends CastSpellAction
	{
		private static class Factory extends SpecialActionFactory
		{
			private final int parentID;

			public Factory(Flashback parent)
			{
				this.parentID = parent.ID;
			}

			@Override
			public java.util.Set<PlayerAction> getActions(GameState state, GameObject source, Player actor)
			{
				if(!source.getOwner(state).equals(state.getPlayerWithPriority()))
					return java.util.Collections.<PlayerAction>emptySet();

				// TODO : This isn't good enough. We need to take into
				// account modifications to timing permissions (like
				// Quicken). A unified way of doing this would be good.
				if(source.getTypes().contains(Type.SORCERY) && !(PlayerCanPlaySorcerySpeed.get(state).contains(source.getController(state))))
					return java.util.Collections.<PlayerAction>emptySet();

				return java.util.Collections.<PlayerAction>singleton(new FlashbackAction(state.game, state.<Flashback>get(this.parentID), source, actor));
			}
		}

		private final int parentID;

		public FlashbackAction(Game game, Flashback parent, GameObject cast, Player caster)
		{
			super(game, cast, new int[] {0}, caster, parent.ID);
			this.name = parent.getName();
			this.parentID = parent.ID;
		}

		@Override
		public GameObject play()
		{
			Set altCost = new Set();

			Flashback parent = this.game.actualState.get(this.parentID);
			if(!parent.flashbackCost.manaCost.isEmpty())
				altCost.addAll(parent.flashbackCost.manaCost);
			altCost.addAll(parent.flashbackCost.events);

			GameObject toBePlayed = this.game.actualState.get(this.toBePlayedID);
			Player casting = this.game.actualState.get(this.actorID);

			EventFactory castEventFactory = new EventFactory(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY, casting + " plays " + toBePlayed + ".");
			castEventFactory.parameters.put(EventType.Parameter.PLAYER, Identity.instance(casting));
			castEventFactory.parameters.put(EventType.Parameter.ACTION, Identity.instance(this));
			castEventFactory.parameters.put(EventType.Parameter.OBJECT, Identity.instance(toBePlayed));
			castEventFactory.parameters.put(EventType.Parameter.ALTERNATE_COST, Identity.fromCollection(altCost));
			Event castEvent = castEventFactory.createEvent(this.game, toBePlayed);
			if(!castEvent.perform(null, true))
				return null;

			GameObject cast = castEvent.getResult().getOne(GameObject.class);
			cast.getPhysical().flashbackCostPaid = true;
			return cast;
		}
	}

	public static final class FlashbackExileReplacement extends StaticAbility
	{
		public static class NotTheExileZonePattern implements SetPattern
		{
			@Override
			public boolean match(GameState state, Identified thisObject, Set set)
			{
				int exileZoneID = state.exileZone().ID;
				for(Zone zone: set.getAll(Zone.class))
					if(zone.ID == exileZoneID)
						return false;
				return true;
			}

			@Override
			public void freeze(GameState state, Identified thisObject)
			{
				// Nothing to freeze (except maybe the exile zone id?)
			}
		}

		public FlashbackExileReplacement(GameState state)
		{
			super(state, "If the flashback cost was paid, exile this card instead of putting it anywhere else any time it would leave the stack.");

			this.canApply = Both.instance(THIS_IS_ON_THE_STACK, WasFlashbacked.instance(This.instance()));

			ZoneChangeReplacementEffect flashbackReplacement = new ZoneChangeReplacementEffect(state.game, "Exile this card instead of putting it anywhere else any time it would leave the stack.");
			flashbackReplacement.addPattern(new SimpleZoneChangePattern(null, new NotTheExileZonePattern(), new SimpleSetPattern(This.instance()), true));
			flashbackReplacement.changeDestination(ExileZone.instance());
			this.addEffectPart(replacementEffectPart(flashbackReplacement));
		}
	}
}
