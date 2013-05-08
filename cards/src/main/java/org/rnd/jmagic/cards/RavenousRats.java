package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ravenous Rats")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_DESTINY, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class RavenousRats extends Card
{
	public static final class CIPDiscard extends EventTriggeredAbility
	{
		public CIPDiscard(GameState state)
		{
			super(state, "When Ravenous Rats enters the battlefield, target opponent discards a card.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");
			this.addEffect(discardCards(targetedBy(target), 1, "Target opponent discards a card."));
		}
	}

	public RavenousRats(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new CIPDiscard(state));
	}
}
