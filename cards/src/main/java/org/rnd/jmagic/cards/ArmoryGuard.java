package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Armory Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.SOLDIER})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class ArmoryGuard extends Card
{
	public static final class ArmoryGuardAbility0 extends StaticAbility
	{
		public ArmoryGuardAbility0(GameState state)
		{
			super(state, "Armory Guard has vigilance as long as you control a Gate.");

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Vigilance.class));

			this.canApply = Both.instance(this.canApply, YouControlAGate.instance());
		}
	}

	public ArmoryGuard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Armory Guard has vigilance as long as you control a Gate.
		this.addAbility(new ArmoryGuardAbility0(state));
	}
}
