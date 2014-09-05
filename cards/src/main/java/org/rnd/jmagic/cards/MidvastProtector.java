package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Midvast Protector")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MidvastProtector extends Card
{
	public static final class MidvastProtectorAbility0 extends EventTriggeredAbility
	{
		public MidvastProtectorAbility0(GameState state)
		{
			super(state, "When Midvast Protector enters the battlefield, target creature you control gains protection from the color of your choice until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			EventFactory chooseColor = playerChoose(You.instance(), 1, Identity.fromCollection(Color.allColors()), PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "");
			this.addEffect(chooseColor);

			SetGenerator color = EffectResult.instance(chooseColor);
			this.addEffect(addProtectionUntilEndOfTurn(target, color, "Target creature gains protection from the color of your choice until end of turn."));
		}
	}

	public MidvastProtector(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// When Midvast Protector enters the battlefield, target creature you
		// control gains protection from the color of your choice until end of
		// turn.
		this.addAbility(new MidvastProtectorAbility0(state));
	}
}
