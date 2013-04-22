package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mishra's Workshop")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.ANTIQUITIES, r = Rarity.RARE)})
@ColorIdentity({})
public final class MishrasWorkshop extends Card
{
	public static final class MishrasWorkshopAbility0 extends ActivatedAbility
	{
		public MishrasWorkshopAbility0(GameState state)
		{
			super(state, "(T): Add (3) to your mana pool. Spend this mana only to cast artifact spells.");
			this.costsTap = true;

			EventFactory mana = new EventFactory(ADD_RESTRICTED_MANA, "Add (3) to your mana pool. Spend this mana only to cast artifact spells.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mana.parameters.put(EventType.Parameter.TYPE, Identity.instance(new TypePattern(Type.ARTIFACT)));
			mana.parameters.put(EventType.Parameter.PERMANENT, Empty.instance());
			mana.parameters.put(EventType.Parameter.MANA, Identity.instance(ManaSymbol.ManaType.COLORLESS));
			mana.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(mana);
		}
	}

	public MishrasWorkshop(GameState state)
	{
		super(state);

		// (T): Add (3) to your mana pool. Spend this mana only to cast artifact
		// spells.
		this.addAbility(new MishrasWorkshopAbility0(state));
	}
}
