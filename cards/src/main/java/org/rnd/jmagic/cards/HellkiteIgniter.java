package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hellkite Igniter")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("5RR")
@ColorIdentity({Color.RED})
public final class HellkiteIgniter extends Card
{
	public static final class HellkiteIgniterAbility1 extends ActivatedAbility
	{
		public HellkiteIgniterAbility1(GameState state)
		{
			super(state, "(1)(R): Hellkite Igniter gets +X/+0 until end of turn, where X is the number of artifacts you control.");
			this.setManaCost(new ManaPool("(1)(R)"));

			SetGenerator number = Count.instance(Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance())));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, number, numberGenerator(0), "Hellkite Igniter gets +X/+0 until end of turn, where X is the number of artifacts you control."));
		}
	}

	public HellkiteIgniter(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// (1)(R): Hellkite Igniter gets +X/+0 until end of turn, where X is the
		// number of artifacts you control.
		this.addAbility(new HellkiteIgniterAbility1(state));
	}
}
