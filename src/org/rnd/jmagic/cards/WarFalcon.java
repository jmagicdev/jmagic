package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("War Falcon")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class WarFalcon extends Card
{
	public static final class WarFalconAbility1 extends StaticAbility
	{
		public WarFalconAbility1(GameState state)
		{
			super(state, "War Falcon can't attack unless you control a Knight or a Soldier.");

			SetGenerator restriction = Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(SubType.KNIGHT, SubType.SOLDIER));
			SetGenerator both = Both.instance(Not.instance(restriction), Intersect.instance(Attacking.instance(), This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(both));
			this.addEffectPart(part);
		}
	}

	public WarFalcon(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// War Falcon can't attack unless you control a Knight or a Soldier.
		this.addAbility(new WarFalconAbility1(state));
	}
}
