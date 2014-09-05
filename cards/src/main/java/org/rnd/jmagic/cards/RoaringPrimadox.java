package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Roaring Primadox")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class RoaringPrimadox extends Card
{
	public static final class RoaringPrimadoxAbility0 extends EventTriggeredAbility
	{
		public RoaringPrimadoxAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, return a creature you control to its owner's hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(bounceChoice(You.instance(), 1, CREATURES_YOU_CONTROL, "Return a creature you control to its owner's hand."));
		}
	}

	public RoaringPrimadox(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// At the beginning of your upkeep, return a creature you control to its
		// owner's hand.
		this.addAbility(new RoaringPrimadoxAbility0(state));
	}
}
