package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aura Finesse")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class AuraFinesse extends Card
{
	public AuraFinesse(GameState state)
	{
		super(state);

		Target targetAura = this.addTarget(Intersect.instance(HasSubType.instance(SubType.AURA), ControlledBy.instance(You.instance())), "target Aura you control");
		Target targetCreature = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(attach(targetedBy(targetAura), targetedBy(targetCreature), "Attach target Aura you control to target creature."));
		this.addEffect(drawACard());
	}
}
