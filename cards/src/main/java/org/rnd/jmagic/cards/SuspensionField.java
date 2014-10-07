package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Suspension Field")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SuspensionField extends Card
{
	public static final class SuspensionFieldAbility0 extends EventTriggeredAbility
	{
		public SuspensionFieldAbility0(GameState state)
		{
			super(state, "When Suspension Field enters the battlefield, you may exile target creature with toughness 3 or greater until Suspension Field leaves the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator legal = HasToughness.instance(Between.instance(3, null));
			Target target = this.addTarget(legal, "target creature an opponent controls");
			this.addEffect(youMay(exileUntilThisLeavesTheBattlefield(state, targetedBy(target), "Exile target creature with toughness 3 or greater until Suspension Field leaves the battlefield.")));
		}
	}

	public SuspensionField(GameState state)
	{
		super(state);

		// When Suspension Field enters the battlefield, you may exile target
		// creature with toughness 3 or greater until Suspension Field leaves
		// the battlefield. (That creature returns under its owner's control.)
		this.addAbility(new SuspensionFieldAbility0(state));
	}
}
