package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crypt Creeper")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class CryptCreeper extends Card
{
	public static final class CryptCreeperAbility0 extends ActivatedAbility
	{
		public CryptCreeperAbility0(GameState state)
		{
			super(state, "Sacrifice Crypt Creeper: Exile target card from a graveyard.");
			this.addCost(sacrificeThis("Crypt Creeper"));

			SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card in a graveyard"));
			this.addEffect(exile(target, "Exile target card from a graveyard."));
		}
	}

	public CryptCreeper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Sacrifice Crypt Creeper: Exile target card from a graveyard.
		this.addAbility(new CryptCreeperAbility0(state));
	}
}
