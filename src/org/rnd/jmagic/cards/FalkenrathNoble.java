package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Falkenrath Noble")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class FalkenrathNoble extends Card
{
	public static final class FalkenrathNobleAbility1 extends EventTriggeredAbility
	{
		public FalkenrathNobleAbility1(GameState state)
		{
			super(state, "Whenever Falkenrath Noble or another creature dies, target player loses 1 life and you gain 1 life.");
			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), CreaturePermanents.instance(), true));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, 1, "Target player loses 1 life"));
			this.addEffect(gainLife(You.instance(), 1, "and you gain 1 life."));
		}
	}

	public FalkenrathNoble(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Falkenrath Noble or another creature dies, target player
		// loses 1 life and you gain 1 life.
		this.addAbility(new FalkenrathNobleAbility1(state));
	}
}
