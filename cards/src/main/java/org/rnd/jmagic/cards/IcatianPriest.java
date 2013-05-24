package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Icatian Priest")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FALLEN_EMPIRES, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class IcatianPriest extends Card
{
	public static final class Bless extends ActivatedAbility
	{
		public Bless(GameState state)
		{
			super(state, "(1)(W)(W): Target creature gets +1/+1 until end of turn.");

			this.setManaCost(new ManaPool("1WW"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+1), (+1), "Target creature gets +1/+1 until end of turn."));
		}
	}

	public IcatianPriest(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Bless(state));
	}
}
