package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Delve")
public final class Delve extends Keyword
{
	public static final String COST_TYPE = "Delve";

	public Delve(GameState state)
	{
		super(state, "Delve");
	}

	@Override
	protected java.util.List<org.rnd.jmagic.engine.StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new DelveAbility(this.state));
	}

	/**
	 * TODO : This is basically just a generic additional cost; is it really
	 * necessary to have a custom effect type? The only reason it was written
	 * that way is that at the time it was written, no "generic" additional cost
	 * effect type existed (only one for additional mana costs).
	 * 
	 * @eparam OBJECT: The object with delve.
	 */
	public static final ContinuousEffectType DELVE_ADDITIONAL_COST = new ContinuousEffectType("DELVE_ADDITIONAL_COST")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "Exile any number of cards from your graveyard");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
			exile.parameters.put(EventType.Parameter.OBJECT, InZone.instance(GraveyardOf.instance(You.instance())));
			exile.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, null));

			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			object.optionalAdditionalCosts.add(new CostCollection(COST_TYPE, exile));
		}

		@Override
		public Layer layer()
		{
			return Layer.RULE_CHANGE;
		}
	};

	/**
	 * @eparam OBJECT: The object with convoke.
	 */
	public static final ContinuousEffectType DELVE_REDUCED_COST = new ContinuousEffectType("DELVE_REDUCED_COST")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

			for(CostCollection cost: object.getOptionalAdditionalCostsChosen())
			{
				if(cost.type.equals(COST_TYPE))
				{
					EventFactory exileCostFactory = cost.events.iterator().next();
					Event exileCost = object.getCostGenerated(state, exileCostFactory);

					// If it hasn't created an event, the spell either wasn't
					// convoked or hasn't been cast yet.
					if(exileCost == null)
						continue;

					// Technically, delve's rules text says "each card
					// exiled this way"... but at the time that cost reductions
					// are applied, there are no creatures tapped, just
					// creatures chosen.
					Set chosenCards = exileCost.getChoices(object.getController(state));
					ManaPool costReduction = new ManaPool("(" + chosenCards.size() + ")");

					// TODO : 702.63b Multiple instances of delve on the same
					// spell are redundant.
					// If a spell has multiple instances of delve, this apply
					// method will be called for each of them.
					state.manaCostReductions.put(new Set(object), costReduction);
				}
			}
		}

		@Override
		public Layer layer()
		{
			return Layer.RULE_CHANGE;
		}
	};

	public static final class DelveAbility extends StaticAbility
	{
		public DelveAbility(GameState state)
		{
			super(state, "You may exile any number of cards from your graveyard as you cast this spell. It costs (1) less to cast for each card exiled this way.");

			ContinuousEffect.Part additionalCost = new ContinuousEffect.Part(DELVE_ADDITIONAL_COST);
			additionalCost.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(additionalCost);

			ContinuousEffect.Part costReduction = new ContinuousEffect.Part(DELVE_REDUCED_COST);
			costReduction.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(costReduction);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}
}
