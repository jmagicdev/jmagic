package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Disciple of Griselbrand")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DiscipleofGriselbrand extends Card
{
	public static final class DiscipleofGriselbrandAbility0 extends ActivatedAbility
	{
		public DiscipleofGriselbrandAbility0(GameState state)
		{
			super(state, "(1), Sacrifice a creature: You gain life equal to the sacrificed creature's toughness.");
			this.setManaCost(new ManaPool("(1)"));

			EventFactory sacrifice = sacrificeACreature();
			this.addCost(sacrifice);

			this.addEffect(gainLife(You.instance(), ToughnessOf.instance(OldObjectOf.instance(EffectResult.instance(sacrifice))), "You gain life equal to the sacrificed creature's toughness."));
		}
	}

	public DiscipleofGriselbrand(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1), Sacrifice a creature: You gain life equal to the sacrificed
		// creature's toughness.
		this.addAbility(new DiscipleofGriselbrandAbility0(state));
	}
}
