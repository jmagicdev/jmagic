package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sejiri Merfolk")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.MERFOLK})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SejiriMerfolk extends Card
{
	public static final class SejiriTraining extends StaticAbility
	{
		public SejiriTraining(GameState state)
		{
			super(state, "As long as you control a Plains, Sejiri Merfolk has first strike and lifelink.");

			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator plains = HasSubType.instance(SubType.PLAINS);
			this.canApply = Both.instance(this.canApply, Intersect.instance(youControl, plains));

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public SejiriMerfolk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// As long as you control a Plains, Sejiri Merfolk has first strike and
		// lifelink.
		this.addAbility(new SejiriTraining(state));
	}
}
