package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thoughtrender Lamia")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.LAMIA})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class ThoughtrenderLamia extends Card
{
	public static final class ThoughtrenderLamiaAbility0 extends EventTriggeredAbility
	{
		public ThoughtrenderLamiaAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Thoughtrender Lamia or another enchantment enters the battlefield under your control, each opponent discards a card.");
			this.addPattern(constellation());
			this.addEffect(discardCards(OpponentsOf.instance(You.instance()), 1, "Each opponent discards a card."));
		}
	}

	public ThoughtrenderLamia(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Constellation \u2014 Whenever Thoughtrender Lamia or another
		// enchantment enters the battlefield under your control, each opponent
		// discards a card.
		this.addAbility(new ThoughtrenderLamiaAbility0(state));
	}
}
