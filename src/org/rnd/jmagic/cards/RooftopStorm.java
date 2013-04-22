package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rooftop Storm")
@Types({Type.ENCHANTMENT})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class RooftopStorm extends Card
{
	public static final class RooftopStormAbility0 extends StaticAbility
	{
		public RooftopStormAbility0(GameState state)
		{
			super(state, "You may pay (0) rather than pay the mana cost for Zombie creature spells you cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(HasSubType.instance(SubType.ZOMBIE), HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance(), Stack.instance())));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new CostCollection(CostCollection.TYPE_ALTERNATE, "(0)")));
			this.addEffectPart(part);
		}
	}

	public RooftopStorm(GameState state)
	{
		super(state);

		// You may pay (0) rather than pay the mana cost for Zombie creature
		// spells you cast.
		this.addAbility(new RooftopStormAbility0(state));
	}
}
