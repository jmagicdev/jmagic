package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Yixlid Jailer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ZOMBIE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class YixlidJailer extends Card
{
	public static final class YixlidJailerAbility0 extends StaticAbility
	{
		public YixlidJailerAbility0(GameState state)
		{
			super(state, "Cards in graveyards lose all abilities.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, InZone.instance(GraveyardOf.instance(Players.instance())));
			this.addEffectPart(part);
		}
	}

	public YixlidJailer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Cards in graveyards lose all abilities.
		this.addAbility(new YixlidJailerAbility0(state));
	}
}
