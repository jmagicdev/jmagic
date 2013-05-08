package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Perimeter Captain")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class PerimeterCaptain extends Card
{
	public static final class DefendersGainLife extends EventTriggeredAbility
	{
		public DefendersGainLife(GameState state)
		{
			super(state, "Whenever a creature you control with defender blocks, you may gain 2 life.");

			SimpleEventPattern blockWithDefender = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			blockWithDefender.put(EventType.Parameter.OBJECT, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Defender.class));
			this.addPattern(blockWithDefender);

			EventFactory gainLife = gainLife(You.instance(), 2, "Gain 2 life");
			this.addEffect(youMay(gainLife, "You may gain 2 life."));
		}
	}

	public PerimeterCaptain(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Whenever a creature you control with defender blocks, you may gain 2
		// life.
		this.addAbility(new DefendersGainLife(state));
	}
}
