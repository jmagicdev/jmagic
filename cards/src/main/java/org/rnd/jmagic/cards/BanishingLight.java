package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Banishing Light")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class BanishingLight extends Card
{
	public static final class BanishingLightAbility0 extends EventTriggeredAbility
	{
		public BanishingLightAbility0(GameState state)
		{
			super(state, "When Banishing Light enters the battlefield, exile target nonland permanent an opponent controls until Banishing Light leaves the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator legalTargets = RelativeComplement.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.LAND));
			SetGenerator target = targetedBy(this.addTarget(legalTargets, "target nonland permanent an opponent controls"));
			this.addEffect(exileUntilThisLeavesTheBattlefield(state, target, "Exile target nonland permanent an opponent controls until Banishing Light leaves the battlefield."));
		}
	}

	public BanishingLight(GameState state)
	{
		super(state);

		// When Banishing Light enters the battlefield, exile target nonland
		// permanent an opponent controls until Banishing Light leaves the
		// battlefield. (That permanent returns under its owner's control.)
		this.addAbility(new BanishingLightAbility0(state));
	}
}
