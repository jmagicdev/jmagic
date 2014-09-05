package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Glimmerpost")
@Types({Type.LAND})
@SubTypes({SubType.LOCUS})
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Glimmerpost extends Card
{
	public static final class GlimmerpostAbility0 extends EventTriggeredAbility
	{
		public GlimmerpostAbility0(GameState state)
		{
			super(state, "When Glimmerpost enters the battlefield, you gain 1 life for each Locus on the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), Count.instance(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.LOCUS))), "You gain 1 life for each Locus on the battlefield."));
		}
	}

	public Glimmerpost(GameState state)
	{
		super(state);

		// When Glimmerpost enters the battlefield, you gain 1 life for each
		// Locus on the battlefield.
		this.addAbility(new GlimmerpostAbility0(state));

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
	}
}
