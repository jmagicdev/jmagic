package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Quirion Dryad")
@Types({Type.CREATURE})
@SubTypes({SubType.DRYAD})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PLANESHIFT, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class QuirionDryad extends Card
{
	public static final class ColorfulPump extends EventTriggeredAbility
	{
		public ColorfulPump(GameState state)
		{
			super(state, "Whenever you cast a white, blue, black, or red spell, put a +1/+1 counter on Quirion Dryad.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator applicableSpells = Intersect.instance(Intersect.instance(Spells.instance(), HasColor.instance(Color.WHITE, Color.BLUE, Color.BLACK, Color.RED)), ControlledBy.instance(ControllerOf.instance(thisCard), Stack.instance()));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.OBJECT, applicableSpells);
			this.addPattern(pattern);

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, thisCard, "Put a +1/+1 counter on Quirion Dryad."));
		}
	}

	public QuirionDryad(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new ColorfulPump(state));
	}
}
