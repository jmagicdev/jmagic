package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Victim of Night")
@Types({Type.INSTANT})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class VictimofNight extends Card
{
	public VictimofNight(GameState state)
	{
		super(state);

		// Destroy target non-Vampire, non-Werewolf, non-Zombie creature.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.VAMPIRE, SubType.WEREWOLF, SubType.ZOMBIE)), "target non--Vampire, non-Werewolf, non-Zombie creature"));
		this.addEffect(destroy(target, "Destroy target non-Vampire, non-Werewolf, non-Zombie creature."));
	}
}
