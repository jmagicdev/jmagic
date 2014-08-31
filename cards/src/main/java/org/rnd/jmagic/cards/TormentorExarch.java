package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tormentor Exarch")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class TormentorExarch extends Card
{
	public static final class TormentorExarchAbility0 extends EventTriggeredAbility
	{
		public TormentorExarchAbility0(GameState state)
		{
			super(state, "When Tormentor Exarch enters the battlefield, choose one \u2014\n\u2022 Target creature gets +2/+0 until end of turn.\n\u2022 Target creature gets -0/-2 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			{
				SetGenerator target = targetedBy(this.addTarget(1, CreaturePermanents.instance(), "target creature"));
				this.addEffect(1, createFloatingEffect("Target creature gets +2/+0 until end of turn;", modifyPowerAndToughness(target, +2, +0)));
			}
			{
				SetGenerator target = targetedBy(this.addTarget(2, CreaturePermanents.instance(), "target creature"));
				this.addEffect(2, createFloatingEffect("Target creature gets -0/-2 until end of turn;", modifyPowerAndToughness(target, -0, -2)));
			}
		}
	}

	public TormentorExarch(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Tormentor Exarch enters the battlefield, choose one \u2014
		// Target creature gets +2/+0 until end of turn; or target creature gets
		// -0/-2 until end of turn.
		this.addAbility(new TormentorExarchAbility0(state));
	}
}
