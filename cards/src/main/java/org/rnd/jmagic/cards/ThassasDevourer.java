package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thassa's Devourer")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class ThassasDevourer extends Card
{
	public static final class ThassasDevourerAbility0 extends EventTriggeredAbility
	{
		public ThassasDevourerAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Thassa's Devourer or another enchantment enters the battlefield under your control, target player puts the top two cards of his or her library into his or her graveyard.");
			this.addPattern(constellation());
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 2, "Target player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public ThassasDevourer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(6);

		// Constellation \u2014 Whenever Thassa's Devourer or another
		// enchantment enters the battlefield under your control, target player
		// puts the top two cards of his or her library into his or her
		// graveyard.
		this.addAbility(new ThassasDevourerAbility0(state));
	}
}
