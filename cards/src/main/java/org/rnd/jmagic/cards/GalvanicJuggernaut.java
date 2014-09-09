package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Galvanic Juggernaut")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.JUGGERNAUT})
@ManaCost("4")
@ColorIdentity({})
public final class GalvanicJuggernaut extends Card
{
	public static final class GalvanicJuggernautAbility2 extends EventTriggeredAbility
	{
		public GalvanicJuggernautAbility2(GameState state)
		{
			super(state, "Whenever another creature dies, untap Galvanic Juggernaut.");
			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), anotherCreature, true));

			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Galvanic Juggernaut"));
		}
	}

	public GalvanicJuggernaut(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Galvanic Juggernaut attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, "Galvanic Juggernaut"));

		// Galvanic Juggernaut doesn't untap during your untap step.
		this.addAbility(new org.rnd.jmagic.abilities.DoesntUntapDuringYourUntapStep(state, "Galvanic Juggernaut"));

		// Whenever another creature dies, untap Galvanic Juggernaut.
		this.addAbility(new GalvanicJuggernautAbility2(state));
	}
}
