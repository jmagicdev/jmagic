package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("\u00C6thersnipe")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class AEthersnipe extends Card
{
	public static final class AEthersnipeAbility0 extends EventTriggeredAbility
	{
		public AEthersnipeAbility0(GameState state)
		{
			super(state, "When \u00C6thersnipe enters the battlefield, return target nonland permanent to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(RelativeComplement.instance(Permanents.instance(), LandPermanents.instance()), "target nonland permanent");
			this.addEffect(bounce(targetedBy(target), "Return target nonland permanent to its owner's hand."));
		}
	}

	public AEthersnipe(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When \u00C6thersnipe enters the battlefield, return target nonland
		// permanent to its owner's hand.
		this.addAbility(new AEthersnipeAbility0(state));

		// Evoke (1)(U)(U) (You may cast this spell for its evoke cost. If you
		// do, it's sacrificed when it enters the battlefield.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evoke(state, "(1)(U)(U)"));
	}
}
