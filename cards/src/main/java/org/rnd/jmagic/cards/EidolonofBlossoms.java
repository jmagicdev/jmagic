package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Eidolon of Blossoms")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class EidolonofBlossoms extends Card
{
	public static final class EidolonofBlossomsAbility0 extends EventTriggeredAbility
	{
		public EidolonofBlossomsAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Eidolon of Blossoms or another enchantment enters the battlefield under your control, draw a card.");
			this.addPattern(constellation());
			this.addEffect(drawACard());
		}
	}

	public EidolonofBlossoms(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Constellation \u2014 Whenever Eidolon of Blossoms or another
		// enchantment enters the battlefield under your control, draw a card.
		this.addAbility(new EidolonofBlossomsAbility0(state));
	}
}
