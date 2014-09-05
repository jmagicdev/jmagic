package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Man-o'-War")
@Types({Type.CREATURE})
@SubTypes({SubType.JELLYFISH})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Starter1999.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Portal.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Visions.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ManoWar extends Card
{
	public static final class ETBBounce extends EventTriggeredAbility
	{
		public ETBBounce(GameState state)
		{
			super(state, "When Man-o'-War enters the battlefield, return target creature to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(bounce(targetedBy(target), "Return target creature to its owner's hand."));
		}
	}

	public ManoWar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Man-o'-War enters the battlefield, return target creature to its
		// owner's hand.
		this.addAbility(new ETBBounce(state));
	}
}
