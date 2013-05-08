package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Timbermaw Larva")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TimbermawLarva extends Card
{
	public static final class LumberGrub extends EventTriggeredAbility
	{
		public LumberGrub(GameState state)
		{
			super(state, "Whenever Timbermaw Larva attacks, it gets +1/+1 until end of turn for each Forest you control.");

			this.addPattern(whenThisAttacks());

			SetGenerator num = Count.instance(Intersect.instance(HasSubType.instance(SubType.FOREST), ControlledBy.instance(You.instance())));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, num, num, "It gets +1/+1 until end of turn for each Forest you control."));
		}
	}

	public TimbermawLarva(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new LumberGrub(state));
	}
}
