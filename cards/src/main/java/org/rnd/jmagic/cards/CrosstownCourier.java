package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crosstown Courier")
@Types({Type.CREATURE})
@SubTypes({SubType.VEDALKEN})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CrosstownCourier extends Card
{
	public static final class CrosstownCourierAbility0 extends EventTriggeredAbility
	{
		public CrosstownCourierAbility0(GameState state)
		{
			super(state, "Whenever Crosstown Courier deals combat damage to a player, that player puts that many cards from the top of his or her library into his or her graveyard.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			SetGenerator thatPlayer = TakerOfDamage.instance(triggerDamage);
			SetGenerator thatMuch = Count.instance(triggerDamage);

			this.addEffect(millCards(thatPlayer, thatMuch, "That player puts that many cards from the top of his or her library into his or her graveyard."));
		}
	}

	public CrosstownCourier(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever Crosstown Courier deals combat damage to a player, that
		// player puts that many cards from the top of his or her library into
		// his or her graveyard.
		this.addAbility(new CrosstownCourierAbility0(state));
	}
}
