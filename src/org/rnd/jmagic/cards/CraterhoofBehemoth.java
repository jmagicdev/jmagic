package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Craterhoof Behemoth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5GGG")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class CraterhoofBehemoth extends Card
{
	public static final class CraterhoofBehemothAbility1 extends EventTriggeredAbility
	{
		public CraterhoofBehemothAbility1(GameState state)
		{
			super(state, "When Craterhoof Behemoth enters the battlefield, creatures you control gain trample and get +X/+X until end of turn, where X is the number of creatures you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator X = Count.instance(CREATURES_YOU_CONTROL);
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, X, X, "Creatures you control gain trample and get +X/+X until end of turn, where X is the number of creatures you control.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public CraterhoofBehemoth(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When Craterhoof Behemoth enters the battlefield, creatures you
		// control gain trample and get +X/+X until end of turn, where X is the
		// number of creatures you control.
		this.addAbility(new CraterhoofBehemothAbility1(state));
	}
}
