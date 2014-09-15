package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Setessan Starbreaker")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class SetessanStarbreaker extends Card
{
	public static final class SetessanStarbreakerAbility0 extends EventTriggeredAbility
	{
		public SetessanStarbreakerAbility0(GameState state)
		{
			super(state, "When Setessan Starbreaker enters the battlefield, you may destroy target Aura.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(HasSubType.instance(SubType.AURA), "target Aura"));

			this.addEffect(youMay(destroy(target, "Destroy target Aura"), "You may destroy target Aura."));
		}
	}

	public SetessanStarbreaker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Setessan Starbreaker enters the battlefield, you may destroy
		// target Aura.
		this.addAbility(new SetessanStarbreakerAbility0(state));
	}
}
