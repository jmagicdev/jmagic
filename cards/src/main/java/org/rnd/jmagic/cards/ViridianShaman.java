package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Viridian Shaman")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class ViridianShaman extends Card
{
	public static final class DestroyArtifact extends EventTriggeredAbility
	{
		public DestroyArtifact(GameState state)
		{
			super(state, "When Viridian Shaman enters the battlefield, destroy target artifact.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");
			this.addEffect(destroy(targetedBy(target), "Destroy target artifact."));
		}
	}

	public ViridianShaman(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new DestroyArtifact(state));
	}
}
