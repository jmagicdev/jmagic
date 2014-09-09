package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aven Riftwatcher")
@Types({Type.CREATURE})
@SubTypes({SubType.REBEL, SubType.SOLDIER, SubType.BIRD})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class AvenRiftwatcher extends Card
{
	public static final class RiftTube extends EventTriggeredAbility
	{
		public RiftTube(GameState state)
		{
			super(state, "When Aven Riftwatcher enters the battlefield or leaves the battlefield, you gain 2 life.");

			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisLeavesTheBattlefield());

			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public AvenRiftwatcher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Vanishing 3
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vanishing(state, 3));

		// When Aven Riftwatcher enters the battlefield or leaves the
		// battlefield, you gain 2 life.
		this.addAbility(new RiftTube(state));
	}
}
