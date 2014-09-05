package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Novablast Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("3GGWW")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class NovablastWurm extends Card
{
	public static final class NovablastWurmAbility0 extends EventTriggeredAbility
	{
		public NovablastWurmAbility0(GameState state)
		{
			super(state, "Whenever Novablast Wurm attacks, destroy all other creatures.");
			this.addPattern(whenThisAttacks());

			SetGenerator otherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), This.instance());
			this.addEffect(destroy(otherCreatures, "Destroy all other creatures."));
		}
	}

	public NovablastWurm(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Whenever Novablast Wurm attacks, destroy all other creatures.
		this.addAbility(new NovablastWurmAbility0(state));
	}
}
