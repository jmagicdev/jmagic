package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sejiri Steppe")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE})
public final class SejiriSteppe extends Card
{
	public static final class ProtectCreature extends EventTriggeredAbility
	{
		public ProtectCreature(GameState state)
		{
			super(state, "When Sejiri Steppe enters the battlefield, target creature you control gains protection from the color of your choice until end of turn.");

			this.addPattern(whenThisEntersTheBattlefield());

			// Target creature gains protection from the color of your choice
			// until
			// end of turn.
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			EventFactory chooseColor = playerChoose(You.instance(), 1, Identity.fromCollection(Color.allColors()), PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "");
			this.addEffect(chooseColor);

			SetGenerator color = EffectResult.instance(chooseColor);
			this.addEffect(addProtectionUntilEndOfTurn(target, color, "Target creature gains protection from the color of your choice until end of turn."));
		}
	}

	public SejiriSteppe(GameState state)
	{
		super(state);

		// Sejiri Steppe enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Sejiri Steppe enters the battlefield, target creature you
		// control gains protection from the color of your choice until end of
		// turn.
		this.addAbility(new ProtectCreature(state));

		// (T): Add (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));
	}
}
