package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flame-Kin Zealot")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.BERSERKER})
@ManaCost("1RRW")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class FlameKinZealot extends Card
{
	public static final class ETBPump extends EventTriggeredAbility
	{
		public ETBPump(GameState state)
		{
			super(state, "When Flame-Kin Zealot enters the battlefield, creatures you control get +1/+1 and gain haste until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator creaturesYouControl = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance()));
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(creaturesYouControl, +1, +1, "Creatures you control get +1/+1 and gain haste until end of turn.", org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public FlameKinZealot(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Flame-Kin Zealot enters the battlefield, creatures you control
		// get +1/+1 and gain haste until end of turn.
		this.addAbility(new ETBPump(state));
	}
}
