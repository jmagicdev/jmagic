package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brood Birthing")
@Types({Type.SORCERY})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BroodBirthing extends Card
{
	public BroodBirthing(GameState state)
	{
		super(state);

		SetGenerator youControlAnEldraziSpawn = Intersect.instance(ControlledBy.instance(You.instance()), Intersect.instance(HasSubType.instance(SubType.ELDRAZI), HasSubType.instance(SubType.SPAWN)));
		SetGenerator numberToCreate = IfThenElse.instance(youControlAnEldraziSpawn, numberGenerator(3), numberGenerator(1));
		this.addEffect(createEldraziSpawnTokens(numberToCreate, "If you control an Eldrazi Spawn, put three 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\" Otherwise, put one of those tokens onto the battlefield."));
	}
}
