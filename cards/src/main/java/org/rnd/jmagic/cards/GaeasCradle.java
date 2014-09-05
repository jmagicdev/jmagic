package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gaea's Cradle")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = UrzasSaga.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class GaeasCradle extends Card
{
	public static final class GaeasCradleAbility0 extends ActivatedAbility
	{
		public GaeasCradleAbility0(GameState state)
		{
			super(state, "(T): Add (G) to your mana pool for each creature you control.");
			this.costsTap = true;

			EventFactory factory = new EventFactory(EventType.ADD_MANA, "Add (G) to your mana pool for each creature you control.");
			factory.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.MANA, Identity.instance(Color.GREEN));
			factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(CREATURES_YOU_CONTROL));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public GaeasCradle(GameState state)
	{
		super(state);

		// (T): Add (G) to your mana pool for each creature you control.
		this.addAbility(new GaeasCradleAbility0(state));
	}
}
