package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Perilous Myr")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("2")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class PerilousMyr extends Card
{
	public static final class PerilousMyrAbility0 extends EventTriggeredAbility
	{
		public PerilousMyrAbility0(GameState state)
		{
			super(state, "When Perilous Myr dies, it deals 2 damage to target creature or player.");
			this.addPattern(whenThisDies());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "It deals 2 damage to target creature or player."));
		}
	}

	public PerilousMyr(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Perilous Myr is put into a graveyard from the battlefield, it
		// deals 2 damage to target creature or player.
		this.addAbility(new PerilousMyrAbility0(state));
	}
}
