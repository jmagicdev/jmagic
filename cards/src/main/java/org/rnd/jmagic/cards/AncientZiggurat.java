package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ancient Ziggurat")
@Types({Type.LAND})
@ColorIdentity({})
public final class AncientZiggurat extends Card
{
	public static final class MakeCreatureMana extends ActivatedAbility
	{
		public MakeCreatureMana(GameState state)
		{
			super(state, "(T): Add one mana of any color to your mana pool. Spend this mana only to cast a creature spell.");

			this.costsTap = true;

			EventFactory addCreatureMana = new EventFactory(ADD_RESTRICTED_MANA, "Add one mana of any color to your mana pool. Spend this mana only to cast creature spells.");
			addCreatureMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addCreatureMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			addCreatureMana.parameters.put(EventType.Parameter.TYPE, Identity.instance(new TypePattern(Type.CREATURE)));
			addCreatureMana.parameters.put(EventType.Parameter.PERMANENT, Empty.instance());
			addCreatureMana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(Color.allColors()));
			this.addEffect(addCreatureMana);
		}
	}

	public AncientZiggurat(GameState state)
	{
		super(state);

		this.addAbility(new MakeCreatureMana(state));
	}
}
