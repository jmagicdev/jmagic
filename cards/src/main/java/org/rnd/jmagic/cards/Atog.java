package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Atog")
@Types({Type.CREATURE})
@SubTypes({SubType.ATOG})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Antiquities.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Atog extends Card
{
	public static final class AtogAbility0 extends ActivatedAbility
	{
		public AtogAbility0(GameState state)
		{
			super(state, "Sacrifice an artifact: Atog gets +2/+2 until end of turn.");
			this.addCost(sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "Sacrifice an artifact"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Atog gets +2/+2 until end of turn."));
		}
	}

	public Atog(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Sacrifice an artifact: Atog gets +2/+2 until end of turn.
		this.addAbility(new AtogAbility0(state));
	}
}
