package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fledgling Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class FledglingGriffin extends Card
{
	public static final class FledglingGriffinAbility0 extends EventTriggeredAbility
	{
		public FledglingGriffinAbility0(GameState state)
		{
			super(state, "Landfall \u2014 Whenever a land enters the battlefield under your control, Fledgling Griffin gains flying until end of turn.");
			this.addPattern(landfall());
			this.addEffect(addAbilityUntilEndOfTurn(AbilitySource.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class, "Fledgling Griffin gains flying until end of turn."));
		}
	}

	public FledglingGriffin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, Fledgling Griffin gains flying until end of turn.
		this.addAbility(new FledglingGriffinAbility0(state));
	}
}
