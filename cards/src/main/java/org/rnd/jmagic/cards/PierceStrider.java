package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pierce Strider")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PierceStrider extends Card
{
	public static final class PierceStriderAbility0 extends EventTriggeredAbility
	{
		public PierceStriderAbility0(GameState state)
		{
			super(state, "When Pierce Strider enters the battlefield, target opponent loses 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(loseLife(target, 3, "Target opponent loses 3 life."));
		}
	}

	public PierceStrider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Pierce Strider enters the battlefield, target opponent loses 3
		// life.
		this.addAbility(new PierceStriderAbility0(state));
	}
}
