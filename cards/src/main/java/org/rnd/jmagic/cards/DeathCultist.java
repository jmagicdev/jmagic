package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Death Cultist")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("B")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DeathCultist extends Card
{
	public static final class DeathCultistAbility0 extends ActivatedAbility
	{
		public DeathCultistAbility0(GameState state)
		{
			super(state, "Sacrifice Death Cultist: Target player loses 1 life and you gain 1 life.");

			this.addCost(sacrificeThis("Death Cultist"));

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(loseLife(targetedBy(target), 1, "Target player loses 1 life"));

			this.addEffect(gainLife(You.instance(), 1, "and you gain 1 life."));
		}
	}

	public DeathCultist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice Death Cultist: Target player loses 1 life and you gain 1
		// life.
		this.addAbility(new DeathCultistAbility0(state));
	}
}
