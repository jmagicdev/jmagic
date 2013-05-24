package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tin Street Hooligan")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.GOBLIN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class TinStreetHooligan extends Card
{
	public static final class TinStreetHooliganAbility0 extends EventTriggeredAbility
	{
		public TinStreetHooliganAbility0(GameState state)
		{
			super(state, "When Tin Street Hooligan enters the battlefield, if (G) was spent to cast Tin Street Hooligan, destroy target artifact.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator colorsSpent = ColorsOf.instance(ManaSpentOn.instance(ABILITY_SOURCE_OF_THIS));
			SetGenerator greenSpent = Intersect.instance(colorsSpent, Identity.instance(Color.GREEN));
			this.interveningIf = greenSpent;

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));

			this.addEffect(destroy(target, "Destroy target artifact."));
		}
	}

	public TinStreetHooligan(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Tin Street Hooligan enters the battlefield, if (G) was spent to
		// cast Tin Street Hooligan, destroy target artifact.
		this.addAbility(new TinStreetHooliganAbility0(state));
	}
}
