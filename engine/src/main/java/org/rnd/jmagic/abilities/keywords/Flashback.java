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

			PlayPermission canFlashback = new PlayPermission(OwnerOf.instance(This.instance()));
			canFlashback.forceAlternateCost(Identity.instance(parent.flashbackCost));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(canFlashback));
			this.addEffectPart(part);
		}

		@Override
		public FlashbackCastAbility create(Game game)
		{
			return new FlashbackCastAbility(game.physicalState, this.parent);
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
