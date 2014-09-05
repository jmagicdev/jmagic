package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Public Execution")
@Types({Type.INSTANT})
@ManaCost("5B")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class PublicExecution extends Card
{
	public PublicExecution(GameState state)
	{
		super(state);

		// Destroy target creature an opponent controls. Each other creature
		// that player controls gets -2/-0 until end of turn.
		SetGenerator creaturesControlledByOpponents = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
		Target target = this.addTarget(creaturesControlledByOpponents, "target creature an opponent controls.");

		EventFactory destroyEvent = destroy(targetedBy(target), "Destroy target creature an opponent controls.");
		this.addEffect(destroyEvent);

		SetGenerator otherCreatures = RelativeComplement.instance(creaturesControlledByOpponents, targetedBy(target));
		this.addEffect(ptChangeUntilEndOfTurn(otherCreatures, (-2), (-0), "Each other creature that player controls gets -2/-0 until end of turn."));
	}
}
