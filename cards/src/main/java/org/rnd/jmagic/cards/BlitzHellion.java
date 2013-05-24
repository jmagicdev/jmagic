package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Blitz Hellion")
@Types({Type.CREATURE})
@SubTypes({SubType.HELLION})
@ManaCost("3RG")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class BlitzHellion extends Card
{
	public static final class Blitz extends EventTriggeredAbility
	{
		public Blitz(GameState state)
		{
			super(state, "At the beginning of the end step, Blitz Hellion's owner shuffles it into his or her library.");

			this.addPattern(atTheBeginningOfTheEndStep());

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventFactory factory = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Blitz Hellion's owner shuffles it into his or her library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, Union.instance(thisCard, OwnerOf.instance(thisCard)));
			this.addEffect(factory);
		}
	}

	public BlitzHellion(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		this.addAbility(new Blitz(state));
	}
}
