package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gigantomancer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("7G")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Gigantomancer extends Card
{
	public static final class GigantomancerAbility0 extends ActivatedAbility
	{
		public GigantomancerAbility0(GameState state)
		{
			super(state, "(1): Target creature you control has base power and toughness 7/7 until end of turn.");
			this.setManaCost(new ManaPool("(1)"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(createFloatingEffect("Target creature you control has base power and toughness 7/7 until end of turn.", setPowerAndToughness(targetedBy(target), 7, 7)));
		}
	}

	public Gigantomancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1): Target creature you control becomes 7/7 until end of turn.
		this.addAbility(new GigantomancerAbility0(state));
	}
}
