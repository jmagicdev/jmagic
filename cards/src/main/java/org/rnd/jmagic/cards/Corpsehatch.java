package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Corpsehatch")
@Types({Type.SORCERY})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Corpsehatch extends Card
{
	public Corpsehatch(GameState state)
	{
		super(state);

		// Destroy target nonblack creature.
		Target target = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK)), "target nonblack creature");
		this.addEffect(destroy(targetedBy(target), "Destroy target nonblack creature."));

		// Put two 0/1 colorless Eldrazi Spawn creature tokens onto the
		// battlefield. They have "Sacrifice this creature: Add (1) to your mana
		// pool."
		this.addEffect(createEldraziSpawnTokens(numberGenerator(2), "Put two 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\""));
	}
}
