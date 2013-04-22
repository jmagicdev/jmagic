package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hate Weaver")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.WIZARD})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class HateWeaver extends Card
{
	public static final class Weave extends ActivatedAbility
	{
		public Weave(GameState state)
		{
			super(state, "(2): Target blue or red creature gets +1/+0 until end of turn.");

			this.setManaCost(new ManaPool("2"));

			SetGenerator targets = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLUE, Color.RED));

			Target target = this.addTarget(targets, "target blue or red creature");

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+1), (+0), "Target blue or red creature gets +1/+0 until end of turn."));
		}
	}

	public HateWeaver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new Weave(state));
	}
}
