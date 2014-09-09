package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Caller of Gales")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class CallerofGales extends Card
{
	public static final class CallCollect extends ActivatedAbility
	{
		public CallCollect(GameState state)
		{
			super(state, "(1)(U), (T): Target creature gains flying until end of turn.");

			this.setManaCost(new ManaPool("1U"));
			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature gains flying until end of turn."));
		}
	}

	public CallerofGales(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new CallCollect(state));
	}
}
