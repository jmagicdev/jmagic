package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wall of Reverence")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.WALL})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class WallofReverence extends Card
{
	public static final class WallofReverenceAbility1 extends EventTriggeredAbility
	{
		public WallofReverenceAbility1(GameState state)
		{
			super(state, "At the beginning of your end step, you may gain life equal to the power of target creature you control.");
			this.addPattern(atTheBeginningOfYourEndStep());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			this.addEffect(youMay(gainLife(You.instance(), PowerOf.instance(target), "Gain life equal to the power of target creature you control."), "You may gain life equal to the power of target creature you control."));
		}
	}

	public WallofReverence(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(6);

		// Defender, flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your end step, you may gain life equal to the
		// power of target creature you control.
		this.addAbility(new WallofReverenceAbility1(state));
	}
}
