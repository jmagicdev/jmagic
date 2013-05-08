package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mortis Dogs")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MortisDogs extends Card
{
	public static final class MortisDogsAbility0 extends EventTriggeredAbility
	{
		public MortisDogsAbility0(GameState state)
		{
			super(state, "Whenever Mortis Dogs attacks, it gets +2/+0 until end of turn.");
			this.addPattern(whenThisAttacks());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "It gets +2/+0 until end of turn."));
		}
	}

	public static final class MortisDogsAbility1 extends EventTriggeredAbility
	{
		public MortisDogsAbility1(GameState state)
		{
			super(state, "When Mortis Dogs dies, target player loses life equal to its power.");
			this.addPattern(whenThisDies());
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, PowerOf.instance(ABILITY_SOURCE_OF_THIS), "Target player loses life equal to its power."));
		}
	}

	public MortisDogs(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Mortis Dogs attacks, it gets +2/+0 until end of turn.
		this.addAbility(new MortisDogsAbility0(state));

		// When Mortis Dogs is put into a graveyard from the battlefield, target
		// player loses life equal to its power.
		this.addAbility(new MortisDogsAbility1(state));
	}
}
