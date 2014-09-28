package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mardu Hordechief")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class MarduHordechief extends Card
{
	public static final class MarduHordechiefAbility0 extends EventTriggeredAbility
	{
		public MarduHordechiefAbility0(GameState state)
		{
			super(state, "When Mardu Hordechief enters the battlefield, if you attacked with a creature this turn, put a 1/1 white Warrior creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
			this.interveningIf = Raid.instance();

			CreateTokensFactory warrior = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Warrior creature token onto the battlefield.");
			warrior.setColors(Color.WHITE);
			warrior.setSubTypes(SubType.WARRIOR);
			this.addEffect(warrior.getEventFactory());
		}
	}

	public MarduHordechief(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Raid \u2014 When Mardu Hordechief enters the battlefield, if you
		// attacked with a creature this turn, put a 1/1 white Warrior creature
		// token onto the battlefield.
		this.addAbility(new MarduHordechiefAbility0(state));
	}
}
