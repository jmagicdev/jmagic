package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Pine Walker")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class PineWalker extends Card
{
	public static final class PineWalkerAbility1 extends EventTriggeredAbility
	{
		public PineWalkerAbility1(GameState state)
		{
			super(state, "Whenever Pine Walker or another creature you control is turned face up, untap that creature.");

			SimpleEventPattern turnFaceUp = new SimpleEventPattern(EventType.TURN_PERMANENT_FACE_UP);
			turnFaceUp.put(EventType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			this.addPattern(turnFaceUp);

			SetGenerator thatCreature = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			this.addEffect(untap(thatCreature, "Untap that creature."));
		}
	}

	public PineWalker(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Morph (4)(G) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(4)(G)"));

		// Whenever Pine Walker or another creature you control is turned face
		// up, untap that creature.
		this.addAbility(new PineWalkerAbility1(state));
	}
}
