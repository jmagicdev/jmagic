package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Street Sweeper")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class StreetSweeper extends Card
{
	public static final class StreetSweeperAbility0 extends EventTriggeredAbility
	{
		public StreetSweeperAbility0(GameState state)
		{
			super(state, "Whenever Street Sweeper attacks, destroy all Auras attached to target land.");
			this.addPattern(whenThisAttacks());

			SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
			SetGenerator auras = HasSubType.instance(SubType.AURA);
			SetGenerator attachedTo = AttachedTo.instance(target);
			this.addEffect(destroy(Intersect.instance(auras, attachedTo), "Destroy all Auras attached to target land."));
		}
	}

	public StreetSweeper(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		// Whenever Street Sweeper attacks, destroy all Auras attached to target
		// land.
		this.addAbility(new StreetSweeperAbility0(state));
	}
}
