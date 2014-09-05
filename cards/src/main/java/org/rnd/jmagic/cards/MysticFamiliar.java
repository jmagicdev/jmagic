package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mystic Familiar")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Torment.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MysticFamiliar extends Card
{
	public static final class ThresholdRacism extends StaticAbility
	{
		public ThresholdRacism(GameState state)
		{
			super(state, "As long as seven or more cards are in your graveyard, Mystic Familiar gets +1/+1 and has protection from black.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +1, +1));

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Protection.FromBlack.class));

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}
	}

	public MysticFamiliar(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new ThresholdRacism(state));
	}
}
