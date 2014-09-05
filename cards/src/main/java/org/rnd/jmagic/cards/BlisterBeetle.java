package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Blister Beetle")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BlisterBeetle extends Card
{
	public static final class Blister extends EventTriggeredAbility
	{
		public Blister(GameState state)
		{
			super(state, "When Blister Beetle enters the battlefield, target creature gets -1/-1 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), -1, -1, "Target creature gets -1/-1 until end of turn."));
		}
	}

	public BlisterBeetle(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Blister(state));
	}
}
