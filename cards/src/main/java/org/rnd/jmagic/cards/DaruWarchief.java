package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Daru Warchief")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Scourge.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class DaruWarchief extends Card
{
	public static final class SoldierFinity extends StaticAbility
	{
		public SoldierFinity(GameState state)
		{
			super(state, "Soldier spells you cast cost (1) less to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(HasSubType.instance(SubType.SOLDIER), ControlledBy.instance(You.instance(), Stack.instance())));
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("1")));
			this.addEffectPart(part);
		}
	}

	public static final class DaruPump extends StaticAbility
	{
		public DaruPump(GameState state)
		{
			super(state, "Soldier creatures you control get +1/+2.");

			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(HasSubType.instance(SubType.SOLDIER), CREATURES_YOU_CONTROL), +1, +2));
		}
	}

	public DaruWarchief(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Soldier spells you cast cost (1) less to cast.
		this.addAbility(new SoldierFinity(state));

		// Soldier creatures you control get +1/+2.
		this.addAbility(new DaruPump(state));
	}
}
