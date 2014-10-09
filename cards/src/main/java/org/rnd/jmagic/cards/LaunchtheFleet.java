package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Launch the Fleet")
@Types({Type.SORCERY})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class LaunchtheFleet extends Card
{
	public static final class MakeSoldiers extends EventTriggeredAbility
	{
		public MakeSoldiers(GameState state)
		{
			super(state, "Whenever this creature attacks, put a 1/1 white Soldier creature token onto the battlefield tapped and attacking.");
			this.addPattern(whenThisAttacks());

			CreateTokensFactory soldier = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield tapped and attacking.");
			soldier.setColors(Color.WHITE);
			soldier.setSubTypes(SubType.SOLDIER);
			soldier.setTappedAndAttacking(null);
			this.addEffect(soldier.getEventFactory());
		}
	}

	public LaunchtheFleet(GameState state)
	{
		super(state);

		// Strive \u2014 Launch the Fleet costs (1) more to cast for each target
		// beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(1)"));

		// Until end of turn, any number of target creatures each gain
		// "Whenever this creature attacks, put a 1/1 white Soldier creature token onto the battlefield tapped and attacking."
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		this.addEffect(addAbilityUntilEndOfTurn(target, MakeSoldiers.class, "Until end of turn, any number of target creatures each gain \"Whenever this creature attacks, put a 1/1 white Soldier creature token onto the battlefield tapped and attacking.\""));
	}
}
