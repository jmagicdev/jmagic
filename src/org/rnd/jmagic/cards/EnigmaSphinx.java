package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Enigma Sphinx")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("4WUB")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class EnigmaSphinx extends Card
{
	public static final class LongTermPlanning extends EventTriggeredAbility
	{
		public LongTermPlanning(GameState state)
		{
			super(state, "When Enigma Sphinx is put into your graveyard from the battlefield, put it into your library third from the top.");
			this.addPattern(whenThisIsPutIntoYourGraveyardFromTheBattlefield());

			EventFactory move = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put Enigma Sphinx into your library third from the top.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.INDEX, numberGenerator(3));
			move.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(TriggerZoneChange.instance(This.instance())));
			this.addEffect(move);
		}
	}

	public EnigmaSphinx(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Enigma Sphinx is put into your graveyard from the battlefield,
		// put it into your library third from the top.
		this.addAbility(new LongTermPlanning(state));

		// Cascade
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cascade(state));
	}
}
