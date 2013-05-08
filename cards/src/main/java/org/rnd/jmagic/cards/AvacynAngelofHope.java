package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Avacyn, Angel of Hope")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WWW")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class AvacynAngelofHope extends Card
{
	public static final class AvacynAngelofHopeAbility1 extends StaticAbility
	{
		public AvacynAngelofHopeAbility1(GameState state)
		{
			super(state, "Avacyn, Angel of Hope and other permanents you control are indestructible.");

			this.addEffectPart(indestructible(ControlledBy.instance(You.instance())));
		}
	}

	public AvacynAngelofHope(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Avacyn, Angel of Hope and other permanents you control are
		// indestructible.
		this.addAbility(new AvacynAngelofHopeAbility1(state));
	}
}
