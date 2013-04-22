package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mana Crypt")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.PROMO, r = Rarity.SPECIAL)})
@ColorIdentity({})
public final class ManaCrypt extends Card
{
	public static final class ManaCryptAbility0 extends EventTriggeredAbility
	{
		public ManaCryptAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, flip a coin. If you lose the flip, Mana Crypt deals 3 damage to you.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory flip = new EventFactory(EventType.FLIP_COIN, "Flip a coin.");
			flip.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory dealDamage = permanentDealDamage(3, You.instance(), "Mana Crypt deals 3 damage to you.");
			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Flip a coin. If you lose the flip, Mana Crypt deals 3 damage to you.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(flip));
			factory.parameters.put(EventType.Parameter.ELSE, Identity.instance(dealDamage));
			this.addEffect(factory);

		}
	}

	public ManaCrypt(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, flip a coin. If you lose the flip,
		// Mana Crypt deals 3 damage to you.
		this.addAbility(new ManaCryptAbility0(state));

		// (T): Add (2) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(2)"));
	}
}
