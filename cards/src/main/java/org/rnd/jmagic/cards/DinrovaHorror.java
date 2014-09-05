package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dinrova Horror")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("4UB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DinrovaHorror extends Card
{
	public static final class DinrovaHorrorAbility0 extends EventTriggeredAbility
	{
		public DinrovaHorrorAbility0(GameState state)
		{
			super(state, "When Dinrova Horror enters the battlefield, return target permanent to its owner's hand, then that player discards a card.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
			this.addEffect(bounce(target, "Return target permanent to its owner's hand,"));
			this.addEffect(discardCards(OwnerOf.instance(target), 1, "then that player discards a card."));
		}
	}

	public DinrovaHorror(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Dinrova Horror enters the battlefield, return target permanent
		// to its owner's hand, then that player discards a card.
		this.addAbility(new DinrovaHorrorAbility0(state));
	}
}
