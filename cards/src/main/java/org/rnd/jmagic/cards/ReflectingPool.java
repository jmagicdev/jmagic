package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reflecting Pool")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({})
public final class ReflectingPool extends Card
{
	public static final class ReflectingPoolAbility extends ActivatedAbility
	{
		public ReflectingPoolAbility(GameState state)
		{
			super(state, "(T): Add to your mana pool one mana of any type that a land you control could produce.");

			this.costsTap = true;

			SetGenerator controllersLand = Intersect.instance(HasType.instance(Type.LAND), ControlledBy.instance(You.instance()));

			EventType.ParameterMap manaParameters = new EventType.ParameterMap();
			manaParameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			manaParameters.put(EventType.Parameter.MANA, CouldBeProducedBy.instance(controllersLand));
			manaParameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.ADD_MANA, manaParameters, "Add to your mana pool one mana of any type that a land you control could produce"));
		}
	}

	public ReflectingPool(GameState state)
	{
		super(state);

		this.addAbility(new ReflectingPoolAbility(state));
	}
}
