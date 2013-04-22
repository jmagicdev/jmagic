package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Parasitic Strix")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.BIRD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ParasiticStrix extends Card
{
	public static final class LifeSwing extends EventTriggeredAbility
	{
		public LifeSwing(GameState state)
		{
			super(state, "When Parasitic Strix enters the battlefield, if you control a black permanent, target player loses 2 life and you gain 2 life.");

			// When Parasitic Strix enters the battlefield,
			this.addPattern(whenThisEntersTheBattlefield());

			// if you control a black permanent,
			this.interveningIf = Intersect.instance(ControlledBy.instance(You.instance()), HasColor.instance(Color.BLACK));

			// target player
			Target target = this.addTarget(Players.instance(), "target player");

			// loses 2 life and you gain 2 life.
			this.addEffect(loseLife(targetedBy(target), 2, "Target player loses 2 life"));
			this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
		}
	}

	public ParasiticStrix(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new LifeSwing(state));
	}
}
