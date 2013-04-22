package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Everflowing Chalice")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class EverflowingChalice extends Card
{
	public static final class Flowing extends ActivatedAbility
	{
		public Flowing(GameState state)
		{
			super(state, "(T): Add (1) to your mana pool for each charge counter on Everflowing Chalice.");
			this.costsTap = true;

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventFactory effect = new EventFactory(EventType.ADD_MANA, "Add (1) to your mana pool for each charge counter on Everflowing Chalice.");
			effect.parameters.put(EventType.Parameter.SOURCE, thisCard);
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.NUMBER, Count.instance(CountersOn.instance(thisCard, Counter.CounterType.CHARGE)));
			effect.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("1")));
			this.addEffect(effect);
		}
	}

	public EverflowingChalice(GameState state)
	{
		super(state);

		// Multikicker (2)
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(2)");
		this.addAbility(ability);
		CostCollection kickerCost = ability.costCollections[0];

		// Everflowing Chalice enters the battlefield with a charge counter on
		// it for each time it was kicked.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Everflowing Chalice", ThisSpellWasKicked.instance(kickerCost), "a charge counter on it for each time it was kicked", Counter.CounterType.CHARGE));

		// (T): Add (1) to your mana pool for each charge counter on Everflowing
		// Chalice.
		this.addAbility(new Flowing(state));
	}
}
