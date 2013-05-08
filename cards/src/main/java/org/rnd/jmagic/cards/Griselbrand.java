package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Griselbrand")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("4BBBB")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class Griselbrand extends Card
{
	public static final class GriselbrandAbility1 extends ActivatedAbility
	{
		public GriselbrandAbility1(GameState state)
		{
			super(state, "Pay 7 life: Draw seven cards.");

			this.addCost(payLife(You.instance(), 7, "Pay 7 life"));
			this.addEffect(drawCards(You.instance(), 7, "Draw seven cards."));
		}
	}

	public Griselbrand(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Flying, lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// Pay 7 life: Draw seven cards.
		this.addAbility(new GriselbrandAbility1(state));
	}
}
