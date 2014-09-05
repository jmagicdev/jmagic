package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Village Survivors")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class VillageSurvivors extends Card
{
	public static final class VillageSurvivorsAbility1 extends StaticAbility
	{
		public VillageSurvivorsAbility1(GameState state)
		{
			super(state, "Fateful hour \u2014 As long as you have 5 or less life, other creatures you control have vigilance.");
			this.canApply = Both.instance(this.canApply, FatefulHour.instance());

			SetGenerator others = RelativeComplement.instance(CREATURES_YOU_CONTROL, This.instance());
			this.addEffectPart(addAbilityToObject(others, org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public VillageSurvivors(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Fateful hour \u2014 As long as you have 5 or less life, other
		// creatures you control have vigilance.
		this.addAbility(new VillageSurvivorsAbility1(state));
	}
}
