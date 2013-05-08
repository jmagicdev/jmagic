package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Convoke")
public final class Convoke extends Keyword
{
	public static class ConvokeCostType implements java.io.Serializable
	{
		private static final long serialVersionUID = 1L;

		private final int convokeID;

		public ConvokeCostType(int convokeID)
		{
			this.convokeID = convokeID;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			ConvokeCostType other = (ConvokeCostType)obj;
			if(this.convokeID != other.convokeID)
				return false;
			return true;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + this.convokeID;
			return result;
		}

		@Override
		public java.lang.String toString()
		{
			return "Convoke";
		}
	}

	public Convoke(GameState state)
	{
		super(state, "Convoke");
	}

	@Override
	protected java.util.List<org.rnd.jmagic.engine.StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new ConvokeAbility(this.state));
	}

	/**
	 * @eparam OBJECT: The object with Convoke
	 * @eparam COST: The CostCollection for tapping creatures for this specific
	 * instance of Convoke (required for 702.48b - Multiple instances of convoke
	 * on the same spell are redundant.)
	 */
	public static final ContinuousEffectType CONVOKE_REDUCED_COST = new ContinuousEffectType("CONVOKE_REDUCED_COST")
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
			CostCollection costCollection = parameters.get(Parameter.COST).getOne(CostCollection.class);

			for(CostCollection cost: object.getOptionalAdditionalCostsChosen())
			{
				if(cost.equals(costCollection))
				{
					EventFactory tapCreaturesCost = cost.events.iterator().next();
					Event tapCost = object.getCostGenerated(state, tapCreaturesCost);

					// If it hasn't created an event, the spell either wasn't
					// convoked or hasn't been cast yet.
					if(tapCost == null)
						continue;

					// Technically, convoke's rules text says "each creature
					// tapped this way"... but at the time that cost reductions
					// are applied, there are no creatures tapped, just
					// creatures chosen.
					Set chosenCreatures = tapCost.getChoices(object.getController(state));
					ManaPool costReduction = new ManaPool();
					for(GameObject tapped: chosenCreatures.getAll(GameObject.class))
					{
						ManaSymbol forThisCreature = new ManaSymbol("");
						forThisCreature.colorless = 1;
						forThisCreature.colors.addAll(tapped.getColors());
						costReduction.add(forThisCreature);
					}

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

	public static final class ConvokeAbility extends StaticAbility
	{
		public ConvokeAbility(GameState state)
		{
			super(state, "Each creature you tap while casting this spell reduces its cost by (1) or by one mana of that creature's color.");

			SetGenerator yourUntappedCreatures = Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL);

			EventFactory tapCreatures = new EventFactory(EventType.TAP_CHOICE, "Tap any number of untapped creatures you control");
			tapCreatures.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapCreatures.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tapCreatures.parameters.put(EventType.Parameter.CHOICE, yourUntappedCreatures);
			tapCreatures.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, null));

			CostCollection convokeCost = new CostCollection(new ConvokeCostType(this.ID), tapCreatures);

			ContinuousEffect.Part additionalCost = new ContinuousEffect.Part(ContinuousEffectType.OPTIONAL_ADDITIONAL_COST);
			additionalCost.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			additionalCost.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(convokeCost));
			this.addEffectPart(additionalCost);

			ContinuousEffect.Part costReduction = new ContinuousEffect.Part(CONVOKE_REDUCED_COST);
			costReduction.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			costReduction.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(convokeCost));
			this.addEffectPart(costReduction);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}
}
