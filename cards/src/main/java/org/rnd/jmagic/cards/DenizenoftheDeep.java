package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Denizen of the Deep")
@Types({Type.CREATURE})
@SubTypes({SubType.SERPENT})
@ManaCost("6UU")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Starter1999.class, r = Rarity.RARE), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DenizenoftheDeep extends Card
{
	public static final class BigBounce extends EventTriggeredAbility
	{
		public BigBounce(GameState state)
		{
			super(state, "When Denizen of the Deep enters the battlefield, return each other creature you control to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator eachOtherCreatureYouControl = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			this.addEffect(bounce(eachOtherCreatureYouControl, "Return each other creature you control to its owner's hand."));
		}
	}

	public DenizenoftheDeep(GameState state)
	{
		super(state);

		this.setPower(11);
		this.setToughness(11);

		this.addAbility(new BigBounce(state));
	}
}
