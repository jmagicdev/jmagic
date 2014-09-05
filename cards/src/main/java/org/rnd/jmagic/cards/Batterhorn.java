package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Batterhorn")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Batterhorn extends Card
{
	public static final class BatterhornAbility0 extends EventTriggeredAbility
	{
		public BatterhornAbility0(GameState state)
		{
			super(state, "When Batterhorn enters the battlefield, you may destroy target artifact.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(youMay(destroy(target, "Destroy target artifact.")));
		}
	}

	public Batterhorn(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// When Batterhorn enters the battlefield, you may destroy target
		// artifact.
		this.addAbility(new BatterhornAbility0(state));
	}
}
