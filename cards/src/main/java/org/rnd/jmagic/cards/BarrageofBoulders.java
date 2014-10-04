package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Barrage of Boulders")
@Types({Type.SORCERY})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class BarrageofBoulders extends Card
{
	public BarrageofBoulders(GameState state)
	{
		super(state);

		// Barrage of Boulders deals 1 damage to each creature you don't
		// control.
		SetGenerator otherPlayersCreatures = RelativeComplement.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance()));
		this.addEffect(permanentDealDamage(1, otherPlayersCreatures, "Barrage of Boulders deals 1 damage to each creature you don't control."));

		// Ferocious \u2014 If you control a creature with power 4 or greater,
		// creatures can't block this turn.
		EventFactory cantBlock = cantBlockThisTurn(CreaturePermanents.instance(), "Creatures can't block this turn.");
		this.addEffect(ifThen(Ferocious.instance(), cantBlock, "Ferocious \u2014 If you control a creature with power 4 or greater, creatures can't block this turn."));
	}
}
