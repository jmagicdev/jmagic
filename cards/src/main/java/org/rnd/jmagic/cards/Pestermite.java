package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pestermite")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.ROGUE})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Pestermite extends Card
{
	public static final class Pester extends EventTriggeredAbility
	{
		public Pester(GameState state)
		{
			super(state, "When Pestermite enters the battlefield, you may tap or untap target permanent.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(Permanents.instance(), "target permanent");

			EventFactory pester = tapOrUntap(targetedBy(target), "target permanent");
			this.addEffect(youMay(pester, "You may tap or untap target permanent."));
		}
	}

	public Pestermite(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Pestermite enters the battlefield, you may tap or untap target
		// permanent.
		this.addAbility(new Pester(state));
	}
}
