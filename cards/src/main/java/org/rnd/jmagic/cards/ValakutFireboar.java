package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Valakut Fireboar")
@Types({Type.CREATURE})
@SubTypes({SubType.BOAR, SubType.ELEMENTAL})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ValakutFireboar extends Card
{
	public static final class ValakutFireboarAbility0 extends EventTriggeredAbility
	{
		public ValakutFireboarAbility0(GameState state)
		{
			super(state, "Whenever Valakut Fireboar attacks, switch its power and toughness until end of turn.");
			this.addPattern(whenThisAttacks());

			ContinuousEffect.Part switchPT = new ContinuousEffect.Part(ContinuousEffectType.SWITCH_POWER_AND_TOUGHNESS);
			switchPT.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(createFloatingEffect("Switch its power and toughness until end of turn.", switchPT));
		}
	}

	public ValakutFireboar(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(7);

		// Whenever Valakut Fireboar attacks, switch its power and toughness
		// until end of turn.
		this.addAbility(new ValakutFireboarAbility0(state));
	}
}
