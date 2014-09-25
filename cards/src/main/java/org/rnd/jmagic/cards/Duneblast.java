package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Duneblast")
@Types({Type.SORCERY})
@ManaCost("4WBG")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class Duneblast extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Duneblast", "Choose up to one creature.", true);

	public Duneblast(GameState state)
	{
		super(state);

		// Choose up to one creature.
		EventFactory choose = playerChoose(You.instance(), 1, CreaturePermanents.instance(), PlayerInterface.ChoiceType.OBJECTS, REASON, "Choose up to one creature.");
		this.addEffect(choose);

		// Destroy the rest.
		SetGenerator theRest = RelativeComplement.instance(CreaturePermanents.instance(), EffectResult.instance(choose));
		this.addEffect(destroy(theRest, "Destroy the rest."));
	}
}
