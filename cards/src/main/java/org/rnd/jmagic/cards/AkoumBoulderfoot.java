package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Akoum Boulderfoot")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GIANT})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class AkoumBoulderfoot extends Card
{
	public static final class ETBPing extends EventTriggeredAbility
	{
		public ETBPing(GameState state)
		{
			super(state, "When Akoum Boulderfoot enters the battlefield, it deals 1 damage to target creature or player.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(permanentDealDamage(1, targetedBy(target), "Akoum Boulderfoot deals 1 damage to target creature or player."));

		}
	}

	public AkoumBoulderfoot(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// When Akoum Boulderfoot enters the battlefield, it deals 1 damage to
		// target creature or player.
		this.addAbility(new ETBPing(state));
	}
}
