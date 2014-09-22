package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dazzling Ramparts")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class DazzlingRamparts extends Card
{
	public static final class DazzlingRampartsAbility1 extends ActivatedAbility
	{
		public DazzlingRampartsAbility1(GameState state)
		{
			super(state, "(1)(W), (T): Tap target creature.");
			this.setManaCost(new ManaPool("(1)(W)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature."));
		}
	}

	public DazzlingRamparts(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(7);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (1)(W), (T): Tap target creature.
		this.addAbility(new DazzlingRampartsAbility1(state));
	}
}
