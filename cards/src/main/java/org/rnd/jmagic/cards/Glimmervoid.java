package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glimmervoid")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class Glimmervoid extends Card
{
	public static final class GlimmervoidAbility0 extends EventTriggeredAbility
	{
		public GlimmervoidAbility0(GameState state)
		{
			super(state, "At the beginning of the end step, if you control no artifacts, sacrifice Glimmervoid.");
			this.addPattern(atTheBeginningOfTheEndStep());

			SetGenerator yourArtifacts = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.ARTIFACT));
			this.interveningIf = Intersect.instance(Count.instance(yourArtifacts), numberGenerator(0));

			this.addEffect(sacrificeThis("Glimmervoid"));
		}
	}

	public Glimmervoid(GameState state)
	{
		super(state);

		// At the beginning of the end step, if you control no artifacts,
		// sacrifice Glimmervoid.
		this.addAbility(new GlimmervoidAbility0(state));

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
