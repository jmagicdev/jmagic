package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Viridian Corrupter")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class ViridianCorrupter extends Card
{
	public static final class ViridianCorrupterAbility1 extends EventTriggeredAbility
	{
		public ViridianCorrupterAbility1(GameState state)
		{
			super(state, "When Viridian Corrupter enters the battlefield, destroy target artifact.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(destroy(target, "Destroy target artifact."));
		}
	}

	public ViridianCorrupter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// When Viridian Corrupter enters the battlefield, destroy target
		// artifact.
		this.addAbility(new ViridianCorrupterAbility1(state));
	}
}
