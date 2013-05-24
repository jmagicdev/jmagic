package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scourglass")
@Types({Type.ARTIFACT})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Scourglass extends Card
{
	public static final class Scour extends ActivatedAbility
	{
		public Scour(GameState state)
		{
			super(state, "(T), Sacrifice Scourglass: Destroy all permanents except for artifacts and lands. Activate this ability only during your upkeep.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Scourglass"));
			this.addEffect(destroy(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.ARTIFACT, Type.LAND)), "all permanents except for artifacts and lands"));
			this.activateOnlyDuringYourUpkeep();
		}
	}

	public Scourglass(GameState state)
	{
		super(state);

		this.addAbility(new Scour(state));
	}
}
