package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Felhide Petrifier")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.MINOTAUR})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class FelhidePetrifier extends Card
{
	public static final class FelhidePetrifierAbility1 extends StaticAbility
	{
		public FelhidePetrifierAbility1(GameState state)
		{
			super(state, "Other Minotaur creatures you control have deathtouch.");
			SetGenerator minotaurs = Intersect.instance(HasSubType.instance(SubType.MINOTAUR), CREATURES_YOU_CONTROL);
			SetGenerator otherMinotaurs = RelativeComplement.instance(minotaurs, This.instance());
			this.addEffectPart(addAbilityToObject(otherMinotaurs, org.rnd.jmagic.abilities.keywords.Deathtouch.class));
		}
	}

	public FelhidePetrifier(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Other Minotaur creatures you control have deathtouch.
		this.addAbility(new FelhidePetrifierAbility1(state));
	}
}
