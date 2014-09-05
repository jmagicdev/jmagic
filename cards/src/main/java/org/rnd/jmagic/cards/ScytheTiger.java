package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Scythe Tiger")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ScytheTiger extends Card
{
	public static final class HungryKitty extends EventTriggeredAbility
	{
		public HungryKitty(GameState state)
		{
			super(state, "When Scythe Tiger enters the battlefield, sacrifice it unless you sacrifice a land.");

			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory sacThisFactory = sacrificeThis("Scythe Tiger");
			EventFactory sacLandFactory = sacrifice(You.instance(), 1, LandPermanents.instance(), "Sacrifice a land");
			this.addEffect(unless(You.instance(), sacThisFactory, sacLandFactory, "Sacrifice it unless you sacrifice a land."));
		}
	}

	public ScytheTiger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));

		this.addAbility(new HungryKitty(state));
	}
}
