package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Swarmborn Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class SwarmbornGiant extends Card
{
	public static final class SwarmbornGiantAbility0 extends EventTriggeredAbility
	{
		public SwarmbornGiantAbility0(GameState state)
		{
			super(state, "When you're dealt combat damage, sacrifice Swarmborn Giant.");
			this.addPattern(new SimpleDamagePattern(null, You.instance(), true));
			this.addEffect(sacrificeThis("Swarmborn Giant"));
		}
	}

	public static final class SwarmbornGiantAbility1 extends ActivatedAbility
	{
		public SwarmbornGiantAbility1(GameState state)
		{
			super(state, "(4)(G)(G): Monstrosity 2.");
			this.setManaCost(new ManaPool("(4)(G)(G)"));
		}
	}

	public static final class SwarmbornGiantAbility2 extends StaticAbility
	{
		public SwarmbornGiantAbility2(GameState state)
		{
			super(state, "As long as Swarmborn Giant is monstrous, it has reach.");
		}
	}

	public SwarmbornGiant(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// When you're dealt combat damage, sacrifice Swarmborn Giant.
		this.addAbility(new SwarmbornGiantAbility0(state));

		// (4)(G)(G): Monstrosity 2. (If this creature isn't monstrous, put two
		// +1/+1 counters on it and it becomes monstrous.)
		this.addAbility(new SwarmbornGiantAbility1(state));

		// As long as Swarmborn Giant is monstrous, it has reach.
		this.addAbility(new SwarmbornGiantAbility2(state));
	}
}
