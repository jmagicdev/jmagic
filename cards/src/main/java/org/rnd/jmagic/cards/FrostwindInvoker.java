package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Frostwind Invoker")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class FrostwindInvoker extends Card
{
	public static final class FrostwindInvokerAbility1 extends ActivatedAbility
	{
		public FrostwindInvokerAbility1(GameState state)
		{
			super(state, "(8): Creatures you control gain flying until end of turn.");

			this.setManaCost(new ManaPool("(8)"));

			this.addEffect(addAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Flying.class, "Creatures you control gain flying until end of turn."));
		}
	}

	public FrostwindInvoker(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (8): Creatures you control gain flying until end of turn.
		this.addAbility(new FrostwindInvokerAbility1(state));
	}
}
