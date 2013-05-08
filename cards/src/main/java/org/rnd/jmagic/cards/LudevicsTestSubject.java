package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ludevic's Test Subject")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
@BackFace(LudevicsAbomination.class)
public final class LudevicsTestSubject extends Card
{
	public static final class LudevicsTestSubjectAbility1 extends ActivatedAbility
	{
		public LudevicsTestSubjectAbility1(GameState state)
		{
			super(state, "(1)(U): Put a hatchling counter on Ludevic's Test Subject. Then if there are five or more hatchling counters on it, remove all of them and transform it.");
			this.setManaCost(new ManaPool("(1)(U)"));

			this.addEffect(putCounters(1, Counter.CounterType.HATCHLING, ABILITY_SOURCE_OF_THIS, "Put a hatchling counter on Ludevic's Test Subject."));

			this.addEffect(ifThen(Intersect.instance(Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.HATCHLING)), Between.instance(5, null)), sequence(removeCounters(null, Counter.CounterType.HATCHLING, ABILITY_SOURCE_OF_THIS, "Remove all of them."), transformThis("Ludevic's Test Subject")), "Then if there are five or more hatchling counters on it, remove all of them and transform it."));
		}
	}

	public LudevicsTestSubject(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (1)(U): Put a hatchling counter on Ludevic's Test Subject. Then if
		// there are five or more hatchling counters on it, remove all of them
		// and transform it.
		this.addAbility(new LudevicsTestSubjectAbility1(state));
	}
}
