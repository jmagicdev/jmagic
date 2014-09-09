package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Hellspark Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class HellsparkElemental extends Card
{
	public static final class Fizzle extends EventTriggeredAbility
	{
		public Fizzle(GameState state)
		{
			super(state, "At the beginning of the end step, sacrifice Hellspark Elemental.");
			this.addPattern(atTheBeginningOfTheEndStep());
			this.addEffect(sacrificeThis("Hellspark Elemental"));
		}
	}

	public HellsparkElemental(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
		this.addAbility(new Fizzle(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(1)(R)"));
	}
}
