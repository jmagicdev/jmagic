package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Manic Vandal")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ManicVandal extends Card
{
	public static final class ManicVandalAbility0 extends EventTriggeredAbility
	{
		public ManicVandalAbility0(GameState state)
		{
			super(state, "When Manic Vandal enters the battlefield, destroy target artifact.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(destroy(target, "Destroy target artifact."));
		}
	}

	public ManicVandal(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Manic Vandal enters the battlefield, destroy target artifact.
		this.addAbility(new ManicVandalAbility0(state));
	}
}
