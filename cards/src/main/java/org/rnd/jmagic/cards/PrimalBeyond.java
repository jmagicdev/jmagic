package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Primal Beyond")
@Types({Type.LAND})
@ColorIdentity({})
public final class PrimalBeyond extends Card
{
	public static final class MakeElementalMana extends ActivatedAbility
	{
		public MakeElementalMana(GameState state)
		{
			super(state, "(T): Add one mana of any color to your mana pool. Spend this mana only to cast an Elemental spell or activate an ability of an Elemental.");

			this.costsTap = true;

			EventType.ParameterMap manaParameters = new EventType.ParameterMap();
			manaParameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			manaParameters.put(EventType.Parameter.PLAYER, You.instance());
			manaParameters.put(EventType.Parameter.TYPE, Identity.instance(new SubTypePattern(SubType.ELEMENTAL)));
			manaParameters.put(EventType.Parameter.MANA, Identity.fromCollection(Color.allColors()));
			this.addEffect(new EventFactory(ADD_RESTRICTED_MANA, manaParameters, "Add one mana of any color to your mana pool. Spend this mana only to cast Elemental spells or activate abilities of Elementals."));
		}
	}

	public PrimalBeyond(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RevealOrThisEntersTapped(state, this.getName(), SubType.ELEMENTAL));
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
		this.addAbility(new MakeElementalMana(state));
	}
}
