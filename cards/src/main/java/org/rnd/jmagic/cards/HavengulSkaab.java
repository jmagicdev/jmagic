package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Havengul Skaab")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.HORROR})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class HavengulSkaab extends Card
{
	public static final class HavengulSkaabAbility0 extends EventTriggeredAbility
	{
		public HavengulSkaabAbility0(GameState state)
		{
			super(state, "Whenever Havengul Skaab attacks, return another creature you control to its owner's hand.");
			this.addPattern(whenThisAttacks());

			SetGenerator anotherCreatureYouControl = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			this.addEffect(bounceChoice(You.instance(), 1, anotherCreatureYouControl, "Return another creature you control to its owner's hand."));
		}
	}

	public HavengulSkaab(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Whenever Havengul Skaab attacks, return another creature you control
		// to its owner's hand.
		this.addAbility(new HavengulSkaabAbility0(state));
	}
}
