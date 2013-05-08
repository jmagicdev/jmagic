package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kitchen Finks")
@Types({Type.CREATURE})
@SubTypes({SubType.OUPHE})
@ManaCost("1(G/W)(G/W)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class KitchenFinks extends Card
{
	public static final class KitchenFinksAbility0 extends EventTriggeredAbility
	{
		public KitchenFinksAbility0(GameState state)
		{
			super(state, "When Kitchen Finks enters the battlefield, you gain 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public KitchenFinks(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// When Kitchen Finks enters the battlefield, you gain 2 life.
		this.addAbility(new KitchenFinksAbility0(state));

		// Persist
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Persist(state));
	}
}
