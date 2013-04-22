package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Ninjutsu extends Keyword
{
	private final String costString;

	public Ninjutsu(GameState state, String cost)
	{
		super(state, "Ninjutsu " + cost);

		this.costString = cost;
	}

	@Override
	public Ninjutsu create(Game game)
	{
		return new Ninjutsu(game.physicalState, this.costString);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new NinjutsuAbility(this.state, this.costString));

		return ret;
	}

	public static final class NinjutsuAbility extends ActivatedAbility
	{
		private final String costString;

		public NinjutsuAbility(GameState state, String cost)
		{
			super(state, cost + ", Reveal this card from your hand, return an unblocked creature you control to its owner's hand: Put this card into play from your hand tapped and attacking.");

			this.costString = cost;

			this.setManaCost(new ManaPool(cost));

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator cardOwner = OwnerOf.instance(thisCard);

			// Reveal this card from your hand
			{
				SetGenerator duration = Identity.instance(Not.instance(Intersect.instance(This.instance(), InZone.instance(Stack.instance()))));

				EventFactory factory = new EventFactory(EventType.REVEAL, "Reveal this card from your hand");
				factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
				factory.parameters.put(EventType.Parameter.OBJECT, thisCard);
				factory.parameters.put(EventType.Parameter.EFFECT, duration);
				this.addCost(factory);
			}

			// Return an unblocked creature you control to its owner's hand
			EventFactory bounceCost = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "return an unblocked creature you control to its owner's hand");
			bounceCost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			bounceCost.parameters.put(EventType.Parameter.PLAYER, cardOwner);
			bounceCost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			bounceCost.parameters.put(EventType.Parameter.CHOICE, Unblocked.instance());
			this.addCost(bounceCost);

			// Put this card into play from your hand tapped and attacking
			{
				EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING, "Put this card onto the battlefield from your hand tapped and attacking.");
				factory.parameters.put(EventType.Parameter.ATTACKER, AttackingID.instance(OldObjectOf.instance(CostResult.instance(bounceCost))));
				factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
				factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
				factory.parameters.put(EventType.Parameter.OBJECT, thisCard);
				this.addEffect(factory);
			}

			this.activateOnlyFromHand();

			// This ability can't be played unless there is an unblocked
			// creature
			this.addActivateRestriction(Not.instance(Unblocked.instance()));
		}

		@Override
		public NinjutsuAbility create(Game game)
		{
			return new NinjutsuAbility(game.physicalState, this.costString);
		}
	}
}
