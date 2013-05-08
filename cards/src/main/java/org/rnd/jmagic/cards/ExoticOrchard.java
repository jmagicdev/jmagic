package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Exotic Orchard")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.RARE)})
@ColorIdentity({})
public final class ExoticOrchard extends Card
{
	public static final class ExoticOrchardAbility extends ActivatedAbility
	{
		public ExoticOrchardAbility(GameState state)
		{
			super(state, "(T): Add to your mana pool one mana of any color that a land an opponent controls could produce.");

			this.costsTap = true;

			SetGenerator opponents = OpponentsOf.instance(You.instance());
			SetGenerator opponentsLand = Intersect.instance(HasType.instance(Type.LAND), ControlledBy.instance(opponents));

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add to your mana pool one mana of any color that a land an opponent controls could produce.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, CouldBeProducedBy.instance(opponentsLand));
			addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(addMana);
		}
	}

	public ExoticOrchard(GameState state)
	{
		super(state);

		// (T): Add to your mana pool one mana of any color that a land an
		// opponent controls could produce.
		this.addAbility(new ExoticOrchardAbility(state));
	}
}
