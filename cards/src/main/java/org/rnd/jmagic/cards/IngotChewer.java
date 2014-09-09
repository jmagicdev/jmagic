package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ingot Chewer")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class IngotChewer extends Card
{
	public static final class ETBDestroyArtifact extends EventTriggeredAbility
	{
		public ETBDestroyArtifact(GameState state)
		{
			super(state, "When Ingot Chewer enters the battlefield, destroy target artifact.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target t = this.addTarget(ArtifactPermanents.instance(), "target artifact");
			this.addEffect(destroy(targetedBy(t), "Destroy target artifact."));
		}
	}

	public IngotChewer(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Ingot Chewer enters the battlefield, destroy target artifact.
		this.addAbility(new ETBDestroyArtifact(state));

		// Evoke (R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evoke(state, "(R)"));
	}
}
