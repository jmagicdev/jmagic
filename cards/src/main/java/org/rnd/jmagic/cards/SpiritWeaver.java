package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spirit Weaver")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SpiritWeaver extends Card
{
	public static final class Weave extends ActivatedAbility
	{
		public Weave(GameState state)
		{
			super(state, "(2): Target green or blue creature gets +0/+1 until end of turn.");

			this.setManaCost(new ManaPool("2"));

			SetGenerator targets = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.GREEN, Color.BLUE));

			Target target = this.addTarget(targets, "target green or blue creature");

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+0), (+1), "Target green or blue creature gets +0/+1 until end of turn."));
		}
	}

	public SpiritWeaver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new Weave(state));
	}
}
