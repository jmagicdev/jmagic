package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oust")
@Types({Type.SORCERY})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Oust extends Card
{
	public Oust(GameState state)
	{
		super(state);

		// Put target creature into its owner's library second from the top. Its
		// controller gains 3 life.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		EventFactory invincibleIsJustAWord = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target creature into its owner's library second from the top.");
		invincibleIsJustAWord.parameters.put(EventType.Parameter.CAUSE, This.instance());
		invincibleIsJustAWord.parameters.put(EventType.Parameter.INDEX, numberGenerator(2));
		invincibleIsJustAWord.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(invincibleIsJustAWord);

		this.addEffect(gainLife(ControllerOf.instance(targetedBy(target)), 3, "Its controller gains 3 life."));
	}
}
