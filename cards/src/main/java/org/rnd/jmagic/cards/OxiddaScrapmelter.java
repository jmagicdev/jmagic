package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oxidda Scrapmelter")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class OxiddaScrapmelter extends Card
{
	public static final class OxiddaScrapmelterAbility0 extends EventTriggeredAbility
	{
		public OxiddaScrapmelterAbility0(GameState state)
		{
			super(state, "When Oxidda Scrapmelter enters the battlefield, destroy target artifact.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(destroy(target, "Destroy target artifact."));
		}
	}

	public OxiddaScrapmelter(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Oxidda Scrapmelter enters the battlefield, destroy target
		// artifact.
		this.addAbility(new OxiddaScrapmelterAbility0(state));
	}
}
